package com.bluewhale.common.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 包装HttpServletRequest，目的是让其输入流可重复读
 *
 * @author curtin 2020-03-31 21:21:14
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private static Logger logger = LoggerFactory.getLogger(RequestWrapper.class);

    /**
     * 存储body数据的容器
     */
    private final byte[] body;
    /**
     * 存储body数据的String形式
     */
    private String bodyStr;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        // 将body数据存储起来
        String bodyStr = getBodyString(request);
        this.body = bodyStr.getBytes(Charset.defaultCharset());
        this.bodyStr = bodyStr;
    }

    /**
     * 获取请求Body
     *
     * @param request request
     * @return String
     */
    public String getBodyString(final ServletRequest request) {
        try {
            return inputStream2String(request.getInputStream());
        } catch (IOException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取请求Body 返回String
     *
     * @return String
     */
    public String getBodyString() {
//        final InputStream inputStream = new ByteArrayInputStream(body);
//
//        return inputStream2String(inputStream);
        return bodyStr;
    }

    /**
     * 获取请求body 返回Map
     *
     * @return Map&lt;String,Object&gt
     */
    public Map<String, Object> getRequestBodyMap() {
//        final InputStream inputStream = new ByteArrayInputStream(body);
        String msg;
        Map<String, Object> bodyMap = new HashMap<>();

        try {
//            String str = inputStream2String(inputStream);
            String str = bodyStr;

            if (str.trim().length() > 0) {
                logger.debug("\n\rHTTP Body--- {}", str);
                try {
                    //针对前端传输的JSON格式
                    bodyMap = JSON.parseObject(str);
                } catch (Exception e) {
                    //针对前端传输的form数据
                    bodyMap = getMapData(str);
                }
            }
        } catch (Exception e) {
            msg = "[REQUEST-BODY-FAIL] parsing your request body got exeception: \n\r" + CommonsUtil.getExceptionStackMsg(e);
            logger.error(msg);
        }
        return bodyMap;
    }

    /**
     * 转化post请求form参数为map
     *
     * @param strPostParam 请求参数
     * @return Map&lt;String,Object&gt
     */
    private static Map<String, Object> getMapData(String strPostParam) {
        if (strPostParam != null && strPostParam.trim().length() > 0) {
            Map<String, Object> returnMap = new HashMap<>();
            String[] strArr = strPostParam.split("&");
            if (strArr.length > 0) {
                for (String aStrArr : strArr) {
                    String[] postParam = aStrArr.split("=");
                    returnMap.put(postParam[0], postParam[1]);
                }
                return returnMap;
            }
        }

        return null;
    }


    /**
     * 将inputStream里的数据读取出来并转换成字符串
     *
     * @param inputStream inputStream
     * @return String
     */
    private String inputStream2String(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    String msg = "[REQUEST-BODY-FAIL] parsing your request body got exeception: \n\r" + CommonsUtil.getExceptionStackMsg(e);
                    logger.error(msg);
                }
            }
        }

        return sb.toString();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);

        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }
}
