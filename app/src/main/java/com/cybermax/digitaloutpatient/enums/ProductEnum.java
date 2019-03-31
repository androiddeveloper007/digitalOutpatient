package com.cybermax.digitaloutpatient.enums;

import android.text.TextUtils;

/**
 * 流程枚举变量
 */
public enum ProductEnum {
    QUHAO("01"), YUJIAN("02"), DENGJI("03"),
    SHOUFEI("04"), JIEZHONG("05"), LIUGUAN("06"),
    TIJIAN("07"), ERBIHOU("08");

    String price;

    ProductEnum(String p) {
        price = p;
    }

    String getValue() {
        return price;
    }

    public static ProductEnum getByValue(String value) {
        for (ProductEnum tradeStatus : values()) {
            if (TextUtils.equals(tradeStatus.getValue() , value)) {
                return tradeStatus;
            }
        }
        return ProductEnum.DENGJI;
    }
}
