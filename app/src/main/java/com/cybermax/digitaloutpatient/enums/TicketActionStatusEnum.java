package com.cybermax.digitaloutpatient.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TicketActionStatusEnum {

    WAITING(0,"等待呼叫"),
    PROCESSING(1,"处理中"),
    PASSED(2,"过号"),
    COMPLETED(3,"完成");

    private String desc;

    private Integer value;

    TicketActionStatusEnum(int value,String desc) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getValue() {
        return value;
    }

    public static String getDesc(int value) {
        for (TicketActionStatusEnum t : TicketActionStatusEnum.values()) {
            if (t.value == value) {
                return t.desc;
            }
        }
        return null;
    }

    public static List<Map<String, Object>> valuesList() {
        List<Map<String, Object>> list = new ArrayList<>(values().length);
        Map<String, Object> map;
        for (TicketActionStatusEnum useEnum : values()) {
            map = new HashMap<>(2);
            map.put("value", useEnum.getValue());
            map.put("desc", useEnum.getDesc());
            list.add(map);
        }
        return list;
    }
}