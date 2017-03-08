package com.hanpfei;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;

public class WebUtils {
    private static Logger logger = Logger.getLogger(WebUtils.class);
    public static Random RANDOM = new Random(System.currentTimeMillis());

    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int timeout) {
        Cookie theCookie = null;
        try {
            theCookie = new Cookie(java.net.URLEncoder.encode(cookieName, "UTF-8"), cookieValue);
        } catch (UnsupportedEncodingException e) {
            logger.error("set cookie error: ", e);
        }
        theCookie.setPath("/");
        theCookie.setMaxAge(timeout);
        response.addCookie(theCookie);
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName, String defaultValue) {
        Cookie cookieList[] = request.getCookies();
        if (cookieList == null || cookieName == null)
            return defaultValue;
        for (int i = 0; i < cookieList.length; i++) {
            try {
                if (cookieList[i].getName().equals(cookieName))
                    return cookieList[i].getValue();
            } catch (Exception e) {
                logger.error("get cookie error: ", e);
            }
        }
        return defaultValue;
    }

    public static void rmCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie cookieList[] = request.getCookies();
        if (cookieList == null) {
            return;
        }

        for (int i = 0; i < cookieList.length; i++) {
            try {
                if (cookieList[i].getName().equals(cookieName)) {
                    cookieList[i].setMaxAge(0);
                    cookieList[i].setPath("/");
                    response.addCookie(cookieList[i]);
                }
            } catch (Exception e) {
                logger.error("rm cookie error: ", e);
            }
        }
    }

    public static void setResponse(ModelMap model, String key, Object result) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (key != null) {
            resultMap.put(key, result);
        }

        setResponse(model, resultMap);
    }

    public static void setResponse(ModelMap model, Map<String, Object> resultMap) {
        model.put("code", 200);
        model.put("message", "");
        model.put("result", resultMap);
    }

    public static Map<String, Object> extractHeader(HttpServletRequest request) {
        Map<String, Object> headerMap = new HashMap<String, Object>(11);
        String productKey = GeneralUtils.getString(request.getHeader(Const.HEADER_MAM_PRODUCTKEY), null);
        if (StringUtils.isBlank(productKey)) {
            // check parameters
            logger.warn("request productKey is null");
            throw new IllegalArgumentException("productKey is null");
        }
        Matcher matcher = Const.START_PATTERN.matcher(productKey);
        if (!matcher.matches()) {// 过滤以特殊符号开头
            while (matcher.find()) {
                productKey = matcher.group();
            }
        }
        headerMap.put(FieldConst.Header.productKey, productKey);
        headerMap.put(FieldConst.Header.deviceId, GeneralUtils.getString(request.getHeader(Const.HEADER_MAM_DEVICEID), null));
        headerMap.put(FieldConst.Header.productDeviceId,
                GeneralUtils.getString(request.getHeader(Const.HEADER_MAM_PRODUCTDEVICEID), null));
        headerMap.put(FieldConst.Header.productUserId,
                GeneralUtils.getString(request.getHeader(Const.HEADER_MAM_PRODUCTUSERID), null));
        headerMap.put(FieldConst.Header.clientTime,
                Long.valueOf(GeneralUtils.getString(request.getHeader(Const.HEADER_MAM_CLIENTTIME), "0")));

        String clientIp = GeneralUtils.getString(request.getHeader(Const.HEADER_MAM_CLIENT_IP), null);
        clientIp = StringUtils.isBlank(clientIp) ? GeneralUtils
                .getString(request.getHeader(Const.HEADER_REAL_IP), null) : clientIp;
        logger.debug("request client ip:" + clientIp);
        if (StringUtils.isBlank(clientIp)) {
            logger.warn("client null,app:" + productKey);
        }
        headerMap.put(FieldConst.Header.clientIp, clientIp);

        headerMap.put(FieldConst.Header.deviceVersion,
                GeneralUtils.getString(request.getHeader(Const.HEADER_DEVICE_V), Const.UNKNOWN));
        headerMap.put(FieldConst.Header.platform, GeneralUtils.getString(request.getHeader(Const.HEADER_PLATFORM), Const.UNKNOWN));
        headerMap.put(FieldConst.Header.appVersion, GeneralUtils.getString(request.getHeader(Const.HEADER_APP_V), Const.UNKNOWN));
        headerMap.put(FieldConst.Header.deviceName,
                GeneralUtils.getString(request.getHeader(Const.HEADER_DEVICE_N), Const.UNKNOWN));
        headerMap.put(FieldConst.Header.sdkVersion,
                GeneralUtils.getString(request.getHeader(Const.HEADER_SDK_V), Const.DEFAULT_SDK));
        return headerMap;
    }
}
