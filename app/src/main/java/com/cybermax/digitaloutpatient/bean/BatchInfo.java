package com.cybermax.digitaloutpatient.bean;

import java.util.Date;

import lombok.Data;

@Data
public class BatchInfo {
    /**
     * 疫苗编码
     */
    private String bacterinCode;

    /**
     * 批号
     */
    private String batchNo;

    /**
     * 生产企业
     */
    private String corporation;

    /**
     * 生产企业
     */
    private String corporationCode;

    /**
     * 有效期
     */
    private Date validDate;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 价格
     */
    private Double price;

    /**
     * 规格
     */
    private String standards;


    /**
     * 疫苗名称
     */
    private String bactName;

}
