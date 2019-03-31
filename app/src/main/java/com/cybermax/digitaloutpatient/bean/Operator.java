package com.cybermax.digitaloutpatient.bean;

import lombok.Data;

@Data
public class Operator {
    /**
     * 票id
     */
    private Integer ticketId;

    /**
     * 票环节id
     */
    private Integer tiacId;

    /**
     * 操作医生
     */
    private String tiacDoctor;

    /**
     * 接种台id
     */
    private Integer wostId;


    /**
     * 流程编码
     */
    private String prtyCode;

    /**
     * 处理结果 ,见TicketActionResultEnum
     */
    private Integer tiacResult;

    /**
     * 处理结果描述
     */
    private String tiacRemark;


    /**
     * 下一个流程编码
     */
    private String nextPrtyCode;
}
