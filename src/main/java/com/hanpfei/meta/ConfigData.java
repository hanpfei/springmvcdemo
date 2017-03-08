package com.hanpfei.meta;

import java.util.HashMap;
import java.util.Map;

public class ConfigData {
    public static final int RES_CODE_SUCCESS = 200;
    public static final int RES_CODE_FAILED = 501;

    public static final String MSG_SUCCESS = "请求成功";
    public static final String MSG_FAILED = "请求失败";

    private int code;
    private String message;
    private Map<String, Object> data;

    public ConfigData(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = new HashMap<String, Object>();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void addData(String key, Object value) {
        this.data.put(key, value);
    }

    public Map<String, Object> getData() {
        return data;
    }
}
