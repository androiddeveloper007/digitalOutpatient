package com.cybermax.digitaloutpatient.enums;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程枚举变量
 */
public enum HttpCodeEnum {
    SUCCESS("1000","操作成功"),
    FAIL("1001","操作失败"),
    ERROR("9999","业务异常");



    private String code;
    private String msg;

    // 构造方法
    private HttpCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static List<Map<String, Object>> valuesList() {
        List<Map<String, Object>> list = new ArrayList<>(values().length);
        Map<String, Object> map;
        for (HttpCodeEnum httpCodeEnum : values()) {
            map = new HashMap<>();
            map.put("code", httpCodeEnum.getCode());
            map.put("msg", httpCodeEnum.getMsg());
            list.add(map);
        }
        return list;
    }
}
