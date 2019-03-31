package com.cybermax.digitaloutpatient.bean;

import com.cybermax.digitaloutpatient.fragment.desk.PretestDeskFragment;

import java.util.List;

import lombok.Data;

@Data
public class PretestHistory {
    /*"currentPage": 1,    //当前页码
		"pageSize": 10,     //页大小
		"totalCount": 5,     //总记录数
		"first": true,       //是否是第一页
		"pageCount": 1,       //总页数
		"last": true,         //是否是最后一页
		"hasNext": false,     //是否有下一页
		"hasPrev": false,     //是否有前一页
		"nextPage": 1,        //下一页页码
		"prevPage": 1        //上一页页码*/
    private List<PretestDeskFragment> pageData;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalCount;
    private boolean first;
    private Integer pageCount;
    private boolean last;
    private boolean hasNext;
    private boolean hasPrev;
    private Integer nextPage;
    private Integer prevPage;
}
