package com.cybermax.digitaloutpatient.enums;

import android.text.TextUtils;

/**
 * 接种部位枚举变量
 */
public enum InoculatePlaceEnum {
    QITA("其它"), KOUFU("口服"), ZUOSHANGBI("左上臂"),
    YOUSHANGBI("右上臂"), ZUODATUI("左大腿"), YOUDATUI("右大腿");

    String place;

    InoculatePlaceEnum(String p) {
        place = p;
    }

    public String getValue() {
        return place;
    }

    public static InoculatePlaceEnum getByValue(String value) {
        for (InoculatePlaceEnum placeStatus : values()) {
            if (TextUtils.equals(placeStatus.getValue() , value)) {
                return placeStatus;
            }
        }
        return InoculatePlaceEnum.QITA;
    }
}
