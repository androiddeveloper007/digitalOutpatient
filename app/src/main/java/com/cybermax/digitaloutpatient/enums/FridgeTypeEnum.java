package com.cybermax.digitaloutpatient.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程枚举变量
 */
public enum FridgeTypeEnum {
    NONE(0,"不使用冰箱"),
    HAIER(1,"海尔");


    private int value;
    private String desc;

    // 构造方法
    private FridgeTypeEnum(int value, String desc) {
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
        for (FridgeTypeEnum deviceTypeEnum : values()) {
            map = new HashMap<>();
            map.put("value", deviceTypeEnum.getValue());
            map.put("desc", deviceTypeEnum.getDesc());
            list.add(map);
        }
        return list;
    }
}
