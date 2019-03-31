package com.cybermax.digitaloutpatient.bean.share;

import java.io.Serializable;

import lombok.Data;

@Data
public class Workstation implements Serializable {

    private Integer id;

    private String wstyCode;

    private Integer wostNo;

    private String wostName;

    private Integer wostRelationId;

    private String prtyCode;

    private String wostShowName;

    /**
     * 工作流程名称
     */
    private String prtyName;

    /**
     * 工作台类型
     */
    private String wstyName;

    public Workstation(String wostShowName){
        this.wostShowName = wostShowName;
    }
    public Workstation(){
    }
}
