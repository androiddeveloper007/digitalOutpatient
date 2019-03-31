package com.cybermax.digitaloutpatient.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class InoculationExam implements Serializable {


    private Long id;

    /**
     * 儿童编码
     */
    private String chilNo;

    /**
     * 问题id
     */
    private Integer queId;

    /**
     * 答案 0=否，1=是
     */
    private int inexResult;

    /**
     * 测试日期
     */
    private Date inexDate;

    /**
     * 票id
     */
    private Integer ticketId;

}