package com.cybermax.digitaloutpatient.netty;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MessageDTO   {
    private String    msguuid;

    private String    prtyCode;

    private  String   nextPrtyCode;

    private Integer    wskid;

    private Integer    ticketid;

    private Date       msgCreatetime;

    private Integer     msgtype;

    private String      data;

    private  String     mediavoice;

}