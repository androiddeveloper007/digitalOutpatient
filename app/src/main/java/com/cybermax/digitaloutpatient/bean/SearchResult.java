package com.cybermax.digitaloutpatient.bean;

import lombok.Data;

@Data
public class SearchResult {

    private String instId;
    private String instProdCode;
    private String instTime;
    private String instBatchNo;
    private String instValidDate;
    private Double instPrice;
    private String instJztjFormat;
    private String instJztj;
    private String instFree;
    private String instInocPosition;
    private String instCorporation;
}