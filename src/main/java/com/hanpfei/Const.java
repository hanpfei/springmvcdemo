package com.hanpfei;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Const {

    public static final String HEADER_MAM_PRODUCTKEY = "X-MAM-ProductKey";
    public static final String HEADER_MAM_DEVICEID = "X-MAM-DeviceId";
    public static final String HEADER_MAM_PRODUCTDEVICEID = "X-MAM-ProductDeviceId";
    public static final String HEADER_MAM_PRODUCTUSERID = "X-MAM-ProductUserId";
    public static final String HEADER_MAM_LOCATION = "X-MAM-Location";
    public static final String HEADER_MAM_COMPRESSED = "X-MAM-Compressed";
    public static final String HEADER_MAM_CLIENTTIME = "X-MAM-ClientTime";
    public static final String HEADER_MAM_CLIENT_IP = "X-MAM-Client-Ip";
    public static final String HEADER_REAL_IP = "X-From-Ip";
    public static final String HEADER_DEVICE_V = "X-NAPM-DeviceVersion";
    public static final String HEADER_PLATFORM = "X-NAPM-Platform";
    public static final String HEADER_APP_V = "X-NAPM-AppVersion";
    public static final String HEADER_SDK_V = "X-NAPM-SDKVersion";
    public static final String HEADER_DEVICE_N = "X-NAPM-DeviceName";

    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final Pattern START_PATTERN = Pattern.compile("[A-Za-z0-9].*");

    public static final String DEFAULT_SDK = "a.1.1.0";

    public static final int WIFI = 5;
    public static final int NONE = 4;
    public static final String WIFI_STR = "WIFI";

    public static final String UNKNOWN = "unknown";
    public static final String DEFAULT_VIEW = "unused_view";
    public static final String CHINA = "中国";
    public static final String AND_TAG = "Android";
    public static final String NULL_VALUE = "null";

    public static final String WEB_KEY = "ky";
    public static final String WEB_PAGE_ID = "pi";
    public static final String WEB_PAGE_URL = "pu";
    public static final String WEB_PLATFORM = "pt";
    public static final String WEB_NAVIGATION = "nt";
    public static final String WEB_RESOURECE = "rt";
    public static final String WEB_JSERROR = "je";
    public static final String WEB_USERTIMING = "ut";

    public static final Map<String, String> STATE_KEY_MAP = new HashMap<String, String>();
    public static final Map<String, String> UT_KEY_MAP = new HashMap<String, String>();
    public static final Map<String, String> UT2_KEY_MAP = new HashMap<String, String>();

    public static final Map<String, String> PAGE_KEY_MAP = new HashMap<String, String>();
    public static final Map<String, String> AJAX_KEY_MAP = new HashMap<String, String>();
    public static final Map<String, String> JS_KEY_MAP = new HashMap<String, String>();
    static {
        // page
        PAGE_KEY_MAP.put("fetchStart", "d");
        PAGE_KEY_MAP.put("redirectStart", "aj");
        PAGE_KEY_MAP.put("redirectEnd", "ak");
        PAGE_KEY_MAP.put("domComplete", "p");
        PAGE_KEY_MAP.put("loadEventStart", "q");
        PAGE_KEY_MAP.put("navigationStart", "a");
        PAGE_KEY_MAP.put("requestStart", "i");
        PAGE_KEY_MAP.put("secureConnectionStart", "al");
        PAGE_KEY_MAP.put("responseEnd", "k");
        PAGE_KEY_MAP.put("type", "s");
        PAGE_KEY_MAP.put("domLoading", "l");
        PAGE_KEY_MAP.put("domInteractive", "m");
        PAGE_KEY_MAP.put("domContentLoadedEventStart", "n");
        PAGE_KEY_MAP.put("domainLookupEnd", "f");
        PAGE_KEY_MAP.put("loadEventEnd", "r");
        PAGE_KEY_MAP.put("responseStart", "j");
        PAGE_KEY_MAP.put("connectEnd", "h");
        PAGE_KEY_MAP.put("unloadEventStart", "b");
        PAGE_KEY_MAP.put("redirectCount", "t");
        PAGE_KEY_MAP.put("domContentLoadedEventEnd", "o");
        PAGE_KEY_MAP.put("connectStart", "g");
        PAGE_KEY_MAP.put("firstPaint", "u");
        PAGE_KEY_MAP.put("unloadEventEnd", "c");
        PAGE_KEY_MAP.put("domainLookupStart", "e");
        // jserror
        JS_KEY_MAP.put("timestamp", "ai");
        JS_KEY_MAP.put("col", "z");
        JS_KEY_MAP.put("name", "af");
        JS_KEY_MAP.put("line", "aa");
        JS_KEY_MAP.put("stack", "ad");
        JS_KEY_MAP.put("msg", "ab");
        JS_KEY_MAP.put("url", "ac");
        JS_KEY_MAP.put("href", "am");
        // ajax
        AJAX_KEY_MAP.put("scriptEnd", "y");
        AJAX_KEY_MAP.put("fetchStart", "d");
        AJAX_KEY_MAP.put("redirectStart", "aj");
        AJAX_KEY_MAP.put("redirectEnd", "ak");
        AJAX_KEY_MAP.put("requestStart", "i");
        AJAX_KEY_MAP.put("responseEnd", "k");
        AJAX_KEY_MAP.put("secureConnectionStart", "al");
        AJAX_KEY_MAP.put("initiatorType", "x");
        AJAX_KEY_MAP.put("startTime", "ag");
        AJAX_KEY_MAP.put("domainLookupEnd", "f");
        AJAX_KEY_MAP.put("ajaxStart", "w");
        AJAX_KEY_MAP.put("duration", "ae");
        AJAX_KEY_MAP.put("ajaxEnd", "v");
        AJAX_KEY_MAP.put("responseStart", "j");
        AJAX_KEY_MAP.put("connectEnd", "h");
        AJAX_KEY_MAP.put("entryType", "ah");
        AJAX_KEY_MAP.put("name", "af");
        AJAX_KEY_MAP.put("connectStart", "g");
        AJAX_KEY_MAP.put("domainLookupStart", "e");

        // mobile
        STATE_KEY_MAP.put("url", "a");
        STATE_KEY_MAP.put("ips", "b");
        STATE_KEY_MAP.put("statusCode", "c");
        STATE_KEY_MAP.put("errorCode", "d");
        STATE_KEY_MAP.put("errorMsg", "e");

        STATE_KEY_MAP.put("network", "f");
        // STATE_KEY_MAP.put("operator", "g");

        STATE_KEY_MAP.put("requestStartTime", "h");
        STATE_KEY_MAP.put("requestEndTime", "i");
        STATE_KEY_MAP.put("responseStartTime", "j");
        STATE_KEY_MAP.put("responseEndTime", "k");
        STATE_KEY_MAP.put("firstPackageTime", "l");
        STATE_KEY_MAP.put("finishHeadersTime", "m");
        STATE_KEY_MAP.put("readEndTime", "n");

        STATE_KEY_MAP.put("dnsTime", "o");
        STATE_KEY_MAP.put("dnsNumber", "p");
        STATE_KEY_MAP.put("dnsFailNumber", "q");

        STATE_KEY_MAP.put("sendBytes", "r");
        STATE_KEY_MAP.put("receivedBytes", "s");

        STATE_KEY_MAP.put("redirectUrls", "t");

        STATE_KEY_MAP.put("requestHeaders", "u");
        STATE_KEY_MAP.put("responseHeaders", "v");
        STATE_KEY_MAP.put("useHttpDns", "w");
        // STATE_KEY_MAP.put("sdkVersion", "w");
        // userTiming
        UT_KEY_MAP.put("userTiming", "ut");
        UT_KEY_MAP.put("duration", "da");
        UT_KEY_MAP.put("name", "nm");
        UT_KEY_MAP.put("startTime", "st");
        UT_KEY_MAP.put("entryType", "et");
        UT_KEY_MAP.put("bt", "ts");// beginTime

        UT2_KEY_MAP.put("userTiming", "ut");
        UT2_KEY_MAP.put("duration", "ae");
        UT2_KEY_MAP.put("name", "af");
        UT2_KEY_MAP.put("startTime", "ag");
        UT2_KEY_MAP.put("entryType", "ah");
        UT2_KEY_MAP.put("bt", "ai");// beginTime
    }

}
