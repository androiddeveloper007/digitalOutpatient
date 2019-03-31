package com.cybermax.digitaloutpatient.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//收费类型
public enum ChargeStatusEnum {

    NOTFREE(0,"自费"),
    FREE(1,"免费");

    private String desc;

    private Integer value;

    ChargeStatusEnum(Integer value, String desc) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getValue() {
        return value;
    }

    public static String getDesc(Integer value) {
        for (ChargeStatusEnum t : ChargeStatusEnum.values()) {
            if (t.value == value) {
                return t.desc;
            }
        }
        return null;
    }

    public static List<Map<String, Object>> valuesList() {
        List<Map<String, Object>> list = new ArrayList<>(values().length);
        Map<String, Object> map;
        for (ChargeStatusEnum useEnum : values()) {
            map = new HashMap<>(2);
            map.put("value", useEnum.getValue());
            map.put("desc", useEnum.getDesc());
            list.add(map);
        }
        return list;
    }
}