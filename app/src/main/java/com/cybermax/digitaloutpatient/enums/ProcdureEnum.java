package com.cybermax.digitaloutpatient.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程枚举变量
 */
public enum ProcdureEnum {
    QUHAO("01","取号"),YUJIAN("02","预检"),DENGJI("03","登记"),
    SHOUFEI("04","收费"),JIEZHONG("05","接种"),LIUGUAN("06","留观"),
    TIJIAN("07","体检"),ERBIHOU("08","耳鼻喉");


    private String value;
    private String desc;

    // 构造方法
    ProcdureEnum(String value, String desc) {
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
        for (ProcdureEnum procdureEnum : values()) {
            map = new HashMap<>();
            map.put("value", procdureEnum.getValue());
            map.put("desc", procdureEnum.getDesc());
            list.add(map);
        }
        return list;
    }
}
