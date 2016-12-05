package com.dreambox.web.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 51void.com
 * </p>
 * User:cjp Date: 2007-5-19 Time: 9:07:45
 */
public class IPUtil {
    public static final long ipToValue(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return -1;
        }
        String[] ips = ip.split("\\.");
        if (ips.length != 4) {
            return -1l;
        }
        String upValue = format(ips[0]) + format(ips[1]) + format(ips[2]) + format(ips[3]);
        return Long.parseLong(upValue);
    }

    public static String format(String intStr) {
        return String.format("%03d", Integer.parseInt(intStr));
    }


    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");
        if (isInvalidRemoteIp(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (isInvalidRemoteIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isInvalidRemoteIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");

        }
        if (isInvalidRemoteIp(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static boolean isInvalidRemoteIp(String ip) {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            return true;
        }
        return "127.0.0.1".equalsIgnoreCase(ip);
    }

}
