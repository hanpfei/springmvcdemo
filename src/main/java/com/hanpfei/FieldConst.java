package com.hanpfei;

/**
 * 原始数据项
 * 
 * @author jiaozhihui@corp.netease.com
 */
public class FieldConst {
    // public static final String productKey = "productKey";
    // public static final String deviceId = "deviceId";
    // public static final String productDeviceId = "productDeviceId";
    // public static final String productUserId = "productUserId";
    // public static final String latitude = "latitude";
    // public static final String longitude = "longitude";
    public static final String timestamp = "timestamp";

    public static final String url = "url";
    public static final String ips = "ips";
    public static final String statusCode = "statusCode";
    public static final String errorCode = "errorCode";
    public static final String errorMsg = "errorMsg";
    public static final String errorName = "errorName";

    public static final String requestTime = "requestTime";
    public static final String responseTime = "responseTime";
    public static final String fpTime = "fpTime";

    public static final String requestStartTime = "requestStartTime";
    public static final String requestEndTime = "requestEndTime";
    public static final String responseStartTime = "responseStartTime";
    public static final String responseEndTime = "responseEndTime";
    public static final String firstPackageTime = "firstPackageTime";
    public static final String finishHeadersTime = "finishHeadersTime";
    public static final String readEndTime = "readEndTime";

    public static final String dnsTime = "dnsTime";
    public static final String dnsNumber = "dnsNumber";
    public static final String dnsFailNumber = "dnsFailNumber";

    public static final String sendBytes = "sendBytes";
    public static final String receivedBytes = "receivedBytes";

    // public static final String sendRate = "sendRate";
    // public static final String receivedRate = "receivedRate";

    public static final String redirectUrls = "redirectUrls";
    public static final String requestHeaders = "requestHeaders";
    public static final String responseHeaders = "responseHeaders";

    // public static final String host = "host";
    // public static final String path = "path";

    public static final String clientIp = "clientIp";
    public static final String operator = "operator";
    public static final String network = "network";
    // 标识请求是否使用了httpdns
    public static final String useHttpDns = "useHttpDns";

    // public static final String errorCount = "errorCount";
    // public static final String requestCount = "requestCount";
    // public static final String httpErrorCount = "httpErrorCount";
    // public static final String networkErrorCount = "networkErrorCount";

    // public static final String sdkVersion = "sdkVersion";

    // all use
    /*
     * public static final String country = "country"; public static final
     * String province = "province";
     */
    public static final String geo = "geo";
    public static final String city = "city";

    public static class Web {
        public static final String key = "key";
        public static final String geo = "geo";
        public static final String city = "city";
        public static final String pageId = "pageId";
        public static final String pageUrl = "pageUrl";
        public static final String platform = "platform";
        public static final String ip = "ip";
        public static final String userAgent = "userAgent";
        public static final String navigationStart = "navigationStart";
        public static final String responseStart = "responseStart";
        public static final String domContentLoadedEventEnd = "domContentLoadedEventEnd";
        public static final String domContentLoadedEventStart = "domContentLoadedEventStart";
        public static final String dominLookupStart = "dominLookupStart";
        public static final String unloadEventStart = "unloadEventStart";
        public static final String connectStart = "connectStart";
        public static final String requestStart = "requestStart";
        public static final String dominLookupEnd = "dominLookupEnd";
        public static final String type = "type";
        public static final String connectEnd = "connectEnd";
        public static final String fetchStart = "fetchStart";
        public static final String domComplete = "domComplete";
        public static final String firstPaint = "firstPaint";

        public static final String redirectCount = "redirectCount";
        public static final String domLoading = "domLoading";
        public static final String domInteractive = "domInteractive";
        public static final String unloadEventEnd = "unloadEventEnd";
        public static final String loadEventEnd = "loadEventEnd";
        public static final String loadEventStart = "loadEventStart";
        public static final String responseEnd = "responseEnd";
        public static final String secureConnectionStart = "secureConnectionStart";
        public static final String redirectEnd = "redirectEnd";
        public static final String redirectStart = "redirectStart";

        public static final String entryType = "entryType";
        public static final String initiatorType = "initiatorType";
        public static final String duration = "duration";
        public static final String startTime = "startTime";
        public static final String ajaxStart = "ajaxStart";
        public static final String ajaxEnd = "ajaxEnd";
        public static final String scriptEnd = "scriptEnd";

        public static final String timestamp = "timestamp";
        public static final String msg = "msg";
        public static final String url = "url";
        public static final String line = "line";
        public static final String col = "col";
        public static final String name = "name";
        public static final String stack = "stack";
        public static final String fileUrl = "fileUrl";
        public static final String href = "href";

        // userTiming
        public static final String userTiming = "userTiming";
        // raw useragent
        public static final String rawUserAgent = "rawUserAgent";
    }

    public static class Diagnose {

        // header
        // need save
        // public static final String productKey = "productKey";
        // public static final String deviceId = "deviceId";
        // public static final String productDeviceId = "productDeviceId";
        // public static final String productUserId = "productUserId";
        public static final String locationInfo = "locationInfo";
        // public static final String clientTime = "clientTime";
        public static final String carrierName = "carrierName";
        public static final String host = "host";
        public static final String netEnvironment = "netEnvironment";
        public static final String pingResult = "pingResult";
        public static final String totalPackets = "totalPackets";
        public static final String packetLossRate = "packetLossRate";
        public static final String minTime = "minTime";
        public static final String avgTime = "avgTime";
        public static final String maxTime = "maxTime";
        public static final String mdevTime = "mdevTime";
        public static final String clientIp = "clientIp";
        public static final String geo = "geo";
        // traceRoute
        public static final String traceRouteResult = "traceRouteResult";
        public static final String nsinfo = "nsinfo";
        public static final String diagnoseStart = "diagnoseStart";
        public static final String diagnoseEnd = "diagnoseEnd";
        // version
        // public static final String deviceVersion = "deviceVersion";
        // public static final String appVersion = "appVersion";
        // public static final String platform = "platform";
        public static final String diagnoseReason = "diagnoseReason";
        public static final String timestamp = "timestamp";

        // don't save
        public static final String domain = "domain";
        public static final String dormain = "dormain";
    }

    public static class Interaction {
        // header
        // need save
        // public static final String productKey = "productKey";
        // public static final String deviceId = "deviceId";
        // public static final String productDeviceId = "productDeviceId";
        // public static final String productUserId = "productUserId";
        // public static final String clientTime = "clientTime";
        // public static final String platform = "platform";
        // public static final String deviceVersion = "deviceVersion";
        // public static final String appVersion = "appVersion";
        // public static final String deviceName = "deviceName";
        // json
        public static final String launchTime = "launchTime";
        public static final String content = "content";
        public static final String classname = "classname";
        public static final String method = "method";
        public static final String methodType = "methodType";
        public static final String threadType = "threadType";
        public static final String signature = "signature";
        public static final String typeName = "typeName";
        public static final String beginTime = "beginTime";
        public static final String endTime = "endTime";
        public static final String viewUUID = "viewUUID";
        // public static final String clientIp = "clientIp";

        // public static final String viewLoadTime = "viewLoadTime";
        // public static final String viewEndTime = "viewEndTime";
        // public static final String methodTime = "methodTime";
    }

    public static class Header {
        public static final String productKey = "productKey";
        public static final String deviceId = "deviceId";
        public static final String productDeviceId = "productDeviceId";
        public static final String productUserId = "productUserId";
        public static final String deviceVersion = "deviceVersion";
        public static final String platform = "platform";
        public static final String appVersion = "appVersion";
        public static final String clientIp = "clientIp";
        public static final String locationInfo = "locationInfo";
        public static final String clientTime = "clientTime";
        public static final String deviceName = "deviceName";
        public static final String sdkVersion = "sdkVersion";
    }
}
