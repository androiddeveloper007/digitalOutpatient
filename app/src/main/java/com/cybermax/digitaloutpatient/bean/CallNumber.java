package com.cybermax.digitaloutpatient.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CallNumber implements Serializable {

    /**
     * ticketId
     */
    private Integer id;

    /**
     * 票流程id
     */
    private Integer tiacId;

    /**
     * 票号
     */
    private String ticketDisplayNo;

    /**
     * 儿童编码
     */
    private String chilNo;

    /**
     * 儿童卡号
     */
    private String chilCardNo;

    /**
     * 儿童姓名
     */
    private String chilName;

    /**
     * 儿童性别
     */
    private String chilSex;

    /**
     * 儿童生日
     */
    private String chilBirthday;

    /**
     * 母亲姓名
     */
    private String chilMother;

    /**
     * 父亲姓名
     */
    private String chilFather;

    /**
     * 总价格
     */
    private double totalPrice;

    /**
     * 是否属于上次未完成恢复
     */
    private boolean recover;

    /**
     * 待接种记录
     */
    private List<Inoculation> inoculations;

}
