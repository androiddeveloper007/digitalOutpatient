package com.cybermax.digitaloutpatient.bean;

import lombok.Data;

@Data
public class Inoculation {

    /**
     * instId : 00C9DB2F-02CF-43E7-8702-0CEB2FEE9C62
     * instBacterin : 卡介苗
     * instTime : 1
     * instBatchNo : 20170101
     * instValidDate : 2017-01-01
     * instPrice : 12.7
     * instInocPosition : 手臂
     * instJztj : 医院
     * instFree : 1
     */

    private String instId;
    /**
     * 接种疫苗名称
     */
    private String instProdCode;
    /**
     * 接种剂次
     */
    private String instTime;
    /**
     * 疫苗批号
     */
    private String instBatchNo;
    /**
     * 疫苗有效期
     */
    private String instValidDate;
    /**
     * 疫苗价格
     */
    private Double instPrice;
    /**
     * 接种途径
     */
    private String instJztjFormat;
    /**
     * 接种途径
     */
    private String instJztj;
    /**
     * 收费情况
     */
    private String instFree;
    /**
     * 部位
     */
    private String instInocPosition;
    /**
     * 厂家编号
     */
    private String instCorporation;

    /**
     * 厂家名称
     */
    private String corporationName;

    /**
     * 接种医生
     */
    private String instInocDoctor;

    /**
     * 接种状态 1待接种   2已接种    3异常   4延后
     */
    private Integer instInocState;

    /**
     * 疫苗编码
     */
    private String bactCode;

    /**
     * 冰箱类型
     */
    private int fridgeType; // 0=不使用冰箱，1=海尔


    private String tiacDoctor;


    private String barCode;

    /**
     * 冰箱设备信息
     */
    private FridgeInfo fridgeInfo;
}
