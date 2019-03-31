package com.lib.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Locale.CHINA;

public class TimeInstance {
    private static final String DEFAULT_FORMAT_DATE = "yyyy年MM月dd日 HH:mm";

    public String getFormatTimeStr() {
        Date ss = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat(DEFAULT_FORMAT_DATE, CHINA);
        return format1.format(ss.getTime());
    }
}
