package com.cybermax.digitaloutpatient.bean;

import java.util.Date;

import lombok.Data;

@Data
public class VacccrkBarcode {
    private String crkType;

    private String crkNo;

    private Date crkDate;

    private String crkBactCode;

    private String crkStandard;

    private String crkBatchno;

    private String crkCropCode;

    private Date crkValidate;

    private String crkBarcode;

    private Integer crkKen;

    private Integer crkNum;

    private Date crkCreateTime;

    private Integer crkQuality;

    private String crkSignno;

    private String crkIsShare; // 1是 0否    是否为多人分

    private int  crkUsed;   // 已经使用的人次数

    private Date crkFristOpenTime;    //时间戳，开启时
}
