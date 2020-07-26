package com.bluewhale.globle;

import com.alibaba.fastjson.JSON;
import com.bluewhale.common.util.CommonsUtil;
import com.bluewhale.common.util.JwtUtils;
import com.bluewhale.common.util.RequestWrapper;
import com.bluewhale.common.util.ServerResponse;
import com.bluewhale.common.net.IPUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;

/**
 * Token认证过滤器
 *
 * @author curtin 2020/3/22 2:31 AM
 */
@Component
public class AuthTokenFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * Token过滤器开关
     */
    @Value("${bluewhale.tokenFilter}")
    private boolean tokenFilter;

    //jwt签发者
    private static final String TOKEN_ISSUER = "BlueWhaleCore";
    //jwt请求者
    private static final String TOKEN_AUDIENCE = "BlueWhaleWeb";

    private HashSet<String> excludesPattern;
    private String contextPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Map<Object, Object> map = new LinkedHashMap<>(10);
        map.put("filterName", filterConfig.getFilterName());

        logger.info("Filter config include : {}", JSON.toJSONString(map));

        String param = filterConfig.getInitParameter("exclusions");
        if (param != null && param.trim().length() != 0) {
            this.excludesPattern = new HashSet<>(Arrays.asList(param.split("\\s*,\\s*")));
        }

        this.contextPath = getContextPath(filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        Map<String, Object> returnMap = new HashMap<>();

        String requestURI = request.getRequestURI();
        if (this.isExclusion(requestURI)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ServletRequest requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
            //请求的URL路径
//            String pathInfo = request.getPathInfo();
//            logger.info("pathInfo:[ {} ]", pathInfo);

//            logger.info("requestURL:[ {} ]", request.getRequestURL());
            logger.info("requestURI:[ {} ]", requestURI);
            String ipAddress = getIpAddress(request);

            boolean isFilter = false;
            String msg;

            //先从请求头获取，如果没有的话再从BODY里获取
            String tokenStr = request.getHeader("access_token");
            if (StringUtils.isBlank(tokenStr)) {
                Map<String, Object> requestBodyMap = new RequestWrapper((HttpServletRequest) requestWrapper).getRequestBodyMap();
                tokenStr = (String) requestBodyMap.get("access_token");
            }

            if (tokenStr == null || tokenStr.length() < 1) {
                logger.error(msg = "[AUTH TOKEN FAILED] There is no token in the request Head.");
                ServerResponse.buildErrorReturnMap(returnMap, msg);
            } else {
                Map<String, Object> claimsMap = JwtUtils.decodeToken(tokenStr);
                if (Boolean.FALSE.equals(claimsMap.get("status"))) {
                    logger.error(msg = (String) claimsMap.get("msg"));
                    ServerResponse.buildErrorReturnMap(returnMap, msg);
                } else {
                    Claims claimsBody = (Claims) claimsMap.get("body");
                    String audience = claimsBody.getAudience();
                    String issuer = claimsBody.getIssuer();
                    if (!TOKEN_AUDIENCE.equals(audience)) {
                        logger.error(msg = "[AUTH TOKEN FAILED] Your token is not from " + TOKEN_AUDIENCE);
                        ServerResponse.buildErrorReturnMap(returnMap, msg);
                    } else if (!TOKEN_ISSUER.equals(issuer)) {
                        logger.error(msg = "[AUTH TOKEN FAILED] Your token is not from " + TOKEN_ISSUER);
                        ServerResponse.buildErrorReturnMap(returnMap, msg);
                    } else {
                        logger.info("[AUTH TOKEN SUCC] Token验证通过,请求IP: [ {} ]", ipAddress);
                        isFilter = true;
                    }
                }
            }

            if (isFilter) {
                filterChain.doFilter(requestWrapper, servletResponse);
            } else {
                PrintWriter writer = null;
                OutputStreamWriter osw = null;
                try {
                    osw = new OutputStreamWriter(servletResponse.getOutputStream(),
                            StandardCharsets.UTF_8);
                    writer = new PrintWriter(osw, true);
                    String jsonStr = JSON.toJSONString(returnMap);
                    writer.write(jsonStr);
                    writer.flush();
                    writer.close();
                    osw.close();
                } catch (Exception e) {
                    logger.error("过滤器返回信息失败:" + CommonsUtil.getExceptionStackMsg(e));
                } finally {
                    if (null != writer) {
                        writer.close();
                    }
                    if (null != osw) {
                        osw.close();
                    }
                }
            }

        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 排除过滤请求
     *
     * @param requestURI 请求路径
     * @return boolean
     */
    private boolean isExclusion(String requestURI) {
        if (!tokenFilter) {
            return true;
        }

        if (this.excludesPattern != null && requestURI != null) {
            if (this.contextPath != null && requestURI.startsWith(this.contextPath)) {
                requestURI = requestURI.substring(this.contextPath.length());
                if (!requestURI.startsWith("/")) {
                    requestURI = "/" + requestURI;
                }
            }

            Iterator<String> var2 = this.excludesPattern.iterator();

            String pattern;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                pattern = var2.next();
            } while (!matches(pattern, requestURI));

            return true;
        } else {
            return false;
        }
    }

    private boolean matches(String pattern, String source) {
        if (pattern != null && source != null) {
            pattern = pattern.trim();
            source = source.trim();
            int start;
            if (pattern.endsWith("*")) {
                start = pattern.length() - 1;
                if (source.length() >= start && pattern.substring(0, start).equals(source.substring(0, start))) {
                    return true;
                }
            } else if (pattern.startsWith("*")) {
                start = pattern.length() - 1;
                if (source.length() >= start && source.endsWith(pattern.substring(1))) {
                    return true;
                }
            } else if (pattern.contains("*")) {
                start = pattern.indexOf("*");
                int end = pattern.lastIndexOf("*");
                if (source.startsWith(pattern.substring(0, start)) && source.endsWith(pattern.substring(end + 1))) {
                    return true;
                }
            } else if (pattern.equals(source)) {
                return true;
            }

            return false;
        } else {
            return false;
        }
    }

    /**
     * 获取 contextPath
     *
     * @param context ServletContext
     * @return contextPath
     */
    private static String getContextPath(ServletContext context) {
        if (context.getMajorVersion() == 2 && context.getMinorVersion() < 5) {
            return null;
        } else {
            try {
                String contextPath = context.getContextPath();
                if (contextPath == null || contextPath.length() == 0) {
                    contextPath = "/";
                }

                return contextPath;
            } catch (NoSuchMethodError var2) {
                return null;
            }
        }
    }

    /**
     * 获取请求ip
     *
     * @param request HttpServletRequest
     * @return String
     */
    private String getIpAddress(HttpServletRequest request) {
        String ipAddress = IPUtil.getIpAddr(request);
        logger.debug("The nearest remote IP is :[ {} ]", ipAddress);
        return ipAddress;
    }

    /**
     * 打印请求头部信息
     *
     * @param request HttpServletRequest
     */
    private void printHeaders(HttpServletRequest request) {
        Enumeration<String> enu = request.getHeaderNames();
        String hd = enu.nextElement();
        Map<String, String> requestMap = new HashMap<>();
        while (enu.hasMoreElements()) {
            requestMap.put(hd, request.getHeader(hd));
            hd = enu.nextElement();
        }
        if (!requestMap.isEmpty()) {
            logger.debug("\n\rHTTP Head--- {}", JSON.toJSONString(requestMap, true));
        }
    }

//    /**
//     * 打印请求Body信息
//     *
//     * @param request HttpServletRequest
//     */
//    private void printBody(HttpServletRequest request) {
//
//        Map<String, Object> requestBodyMap = getRequestBodyMap(request);
//        if (requestBodyMap.get("body") != null) {
//            logger.debug("\n\rHTTP Body--- {}", JSON.toJSONString(requestBodyMap.get("body"), true));
//        }
//    }
//
//    /**
//     * 获取请求body
//     *
//     * @param request HttpServletRequest
//     * @return Map&lt;String,Object&gt
//     */
//    private Map<String, Object> getRequestBodyMap(HttpServletRequest request) {
//        Map<String, Object> rst = new HashMap<>();
//        boolean statusFlag;
//        String msg;
//        Map<String, Object> bodyMap = new HashMap<>();
//
//        StringBuilder sb = new StringBuilder();
//        InputStream inputStream = null;
//        BufferedReader reder = null;
//
//        try {
//            inputStream = request.getInputStream();
//            reder = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
//            String line;
//            while ((line = reder.readLine()) != null) {
//                sb.append(line);
//            }
//            String str = sb.toString();
//            if (str.trim().length() > 0) {
//                try {
//                    //针对前端传输的JSON格式
//                    bodyMap = JSON.parseObject(str);
//                } catch (Exception e) {
//                    //针对前端传输的form数据
//                    bodyMap = getMapData(str);
//                }
//            }
//            statusFlag = true;
//            msg = "Succ";
//        } catch (Exception e) {
//            msg = "[REQUEST-BODY-FAIL] parsing your request body got exeception: \n\r" + CommonsUtil.getExceptionStackMsg(e);
//            logger.error(msg);
//            statusFlag = false;
//        } finally {
//            try {
//                if (reder != null) {
//                    try {
//                        reder.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (inputStream != null) {
//                    try {
//                        inputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            } catch (Exception e) {
//                msg = "[REQUEST-BODY-FAIL] parsing your request body got exeception: \n\r" + CommonsUtil.getExceptionStackMsg(e);
//                logger.error(msg);
//                statusFlag = false;
//            }
//        }
//        rst.put("status", statusFlag);
//        rst.put("msg", msg);
//        if (bodyMap != null && (!bodyMap.isEmpty())) {
//            logger.debug("\n\rHTTP Body--- {}", JSON.toJSONString(bodyMap, true));
//        }
//        rst.put("body", bodyMap);
//        return rst;
//    }
//
//    /**
//     * 转化post请求form参数为map
//     *
//     * @param strPostParam 请求参数
//     * @return Map&lt;String,Object&gt
//     */
//    public static Map<String, Object> getMapData(String strPostParam) {
//        if (strPostParam != null && strPostParam.trim().length() > 0) {
//            Map<String, Object> returnMap = new HashMap<>();
//            String[] strArr = strPostParam.split("&");
//            if (strArr.length > 0) {
//                for (String aStrArr : strArr) {
//                    String[] postParam = aStrArr.split("=");
//                    returnMap.put(postParam[0], postParam[1]);
//                }
//                return returnMap;
//            }
//        }
//
//        return null;
//    }
}
