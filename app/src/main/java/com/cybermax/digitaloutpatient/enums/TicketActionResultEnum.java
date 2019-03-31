package com.cybermax.digitaloutpatient.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TicketActionResultEnum {

//    0=可接种，1=延迟接种，2=不可接种，3=结束, 4=使用默认流程
    INOC_ENABLED(0, "合格可接种"),
    EXAM_ENABLED(1, "合格可体检"),
    INOC_DISABLED(2, "不合格"),
    OVER(3, "结束"),
    DEFAULT(4, "使用默认流程"),
    EXAM_TO_INOC(5, "体检完成转接种"),
    EXAM_OVER(6, "体检完成");

    private int value;

    private String desc;

    TicketActionResultEnum(int value, String desc) {
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
        for (TicketActionResultEnum useEnum : values()) {
            map = new HashMap<>(2);
            map.put("value", useEnum.getValue());
            map.put("desc", useEnum.getDesc());
            list.add(map);
        }
        return list;
    }
}