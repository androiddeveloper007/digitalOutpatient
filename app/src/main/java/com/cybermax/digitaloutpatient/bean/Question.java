package com.cybermax.digitaloutpatient.bean;

import lombok.Data;

@Data
public class Question {
//    private  String  id;
//    private  String  itemname;
//    private  String  result;
    private Integer queId;

    /**
     * 问题内容
     */
    private String queContent;

    /**
     * 0-体检
     */
    private Integer queType;


    private Integer queQueue;

    private Integer inexResult;
}
