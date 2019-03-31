package com.cybermax.digitaloutpatient.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class Child implements Serializable {

    private String chilNo;

    private String chilCode;

    private String chilCardNo;

    private String chilCardDate;

    private String chilName;

    private String chilSex;

    private String chilBirthday;

    private String chilBirthWeight;

    private String chilMother;

    private String chilFather;

    private String chilResidence;

    private String chilTel;

    private String chilMobile;

    private String chilCurDepartment;

    private String chilAddress;

    private int chilYear;
}
