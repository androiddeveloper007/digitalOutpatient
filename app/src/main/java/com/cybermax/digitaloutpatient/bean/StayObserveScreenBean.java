package com.cybermax.digitaloutpatient.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class StayObserveScreenBean implements Serializable {

    /**
     * chilName : 二哥
     * sex : 男
     * chilBirthday : 2007-07-16
     * chilCardNo : 2007718363
     * ticketNo : V3
     * tiacStatus : 1
     * restTime : 0
     * startTime : 2018-11-26 11:22:07.843
     * endTime : 2018-11-26 11:52:07.843
     */

    private String chilName;
    private String sex;
    private String chilBirthday;
    private String chilCardNo;
    public String ticketNo;
    private Integer tiacStatus;
    private Long restTime;
    private Date startTime;
    private Date endTime;
}
