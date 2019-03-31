package com.cybermax.digitaloutpatient.bean;

import java.util.List;


import lombok.Data;

@Data
public class QueueInfo {
    /**
     * 等待数
     */
    private int waitingCount;

    /**
     * 等待编号
     */
    private List<String> waitingNos;

    /**
     * 过号数
     */
    private int passCount;

    /**
     * 过号编号
     */
    private List<String> passNos;

    /**
     * 完成数
     */
    private int completedCount;

    /**
     * 完成票编号
     */
    private List<String> completedNos;
}