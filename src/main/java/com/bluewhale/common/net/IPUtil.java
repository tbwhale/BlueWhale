package com.bluewhale.common.net;

import javax.servlet.http.HttpServletRequest;

/**
 * IP工具类
 * Created by curtin
 * User: curtin
 * Date: 2020/3/22
 * Time: 9:52 PM
 */
public class IPUtil {

    /**
     * 获取IP信息
     * @param request HttpServletRequest
     * @return String
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && validAddress(ip)) {
            ip = null;
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (ip != null && validAddress(ip)) {
                ip = null;
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            if (ip != null && validAddress(ip)) {
                ip = null;
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            if (ip != null && validAddress(ip)) {
                ip = null;
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            if (ip != null && validAddress(ip)) {
                ip = null;
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip != null && validAddress(ip)) {
                ip = null;
            }
        }

        return ip;
    }

    /**
     * IP校验
     * @param ip ip信息
     * @return boolean
     */
    private static boolean validAddress(String ip) {
        if (ip == null) {
            return true;
        } else {
            for(int i = 0; i < ip.length(); ++i) {
                char ch = ip.charAt(i);
                if ((ch < '0' || ch > '9') && (ch < 'A' || ch > 'F') && (ch < 'a' || ch > 'f') && ch != '.' && ch != ':') {
                    return true;
                }
            }

            return false;
        }
    }
}
