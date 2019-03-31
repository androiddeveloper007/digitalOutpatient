package com.cybermax.digitaloutpatient.bean;

import java.util.Date;

import lombok.Data;

@Data
public class Ticket {

    /**
     * id : 15
     * ticketId : 7
     * tiacStatus : 0
     * tiacCreateTime : 2018-11-24 16:46:22.177
     * tiacCompleteTime : null
     * tiacOverpassTime : null
     * prtyCode : 03
     * tiacDelete : 0
     * tiacLastId : null
     * wostId : null
     * chilName : 二哥
     * chilSex : 男
     * chilNo : 4403030405001151
     * chilCardNo : 2007718363
     * chilBirthday : 2007-07-16
     * ticketDisplayNo : V3
     */

    private int id;
    private int ticketId;
    private int tiacStatus;
    private Date tiacCreateTime;
    private Date tiacCompleteTime;
    private Date tiacOverpassTime;
    private String prtyCode;
    private int tiacDelete;
    private Integer tiacLastId;
    private Integer wostId;
    private String chilName;
    private String chilSex;
    private String chilNo;
    private String chilCardNo;
    private String chilBirthday;
    private String ticketDisplayNo;
    private String wostShowName;

    private int waitTime;
    private  Integer   isBlank;
}
