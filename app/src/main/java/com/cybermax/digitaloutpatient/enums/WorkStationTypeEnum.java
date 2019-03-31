package com.cybermax.digitaloutpatient.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum WorkStationTypeEnum {
     QUHAOJI("01","取号机"),
     GONGZUOTAI("02","工作台"),
     DAPING("03","大屏"),
     XIAOPING("04","小屏"),
     ZONGHEPING("05","综合大屏"),
     LIUGUANJI("06","留观机"),
     YINXIANG("07","音响");

    private String value;
    private String desc;

    // 构造方法
    private WorkStationTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static List<Map<String, Object>> valuesList() {
        List<Map<String, Object>> list = new ArrayList<>(values().length);
        Map<String, Object> map;
        for (WorkStationTypeEnum workStationTypeEnum : values()) {
            map = new HashMap<>();
            map.put("value", workStationTypeEnum.getValue());
            map.put("desc", workStationTypeEnum.getDesc());
            list.add(map);
        }
        return list;
    }
}


