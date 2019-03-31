package com.cybermax.digitaloutpatient.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum  MessageTypeEnum {
    NOTICE(0, "通知消息"),
    MEDIA(1, "语音消息"),;

    private int value;

    private String desc;

    MessageTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static List<Map<String, Object>> valuesList() {
        List<Map<String, Object>> list = new ArrayList<>(values().length);
        Map<String, Object> map;
        for (MessageTypeEnum typeEnum : values()) {
            map = new HashMap<>(2);
            map.put("value", typeEnum.getValue());
            map.put("desc", typeEnum.getDesc());
            list.add(map);
        }
        return list;
    }
}
