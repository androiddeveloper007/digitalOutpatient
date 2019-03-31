package com.cybermax.digitaloutpatient.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 页数
     */
    private int pageNum;

    /**
     * 每页显示条数
     */
    private int pageSize;

    /**
     * 总数
     */
    private long total;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 分页数据
     */
    private List<T> list;

    /**
     * 上一页
     */
    private int prePage;

    /**
     * 下一页
     */
    private int nextPage;

    /**
     * 当前页是否为第一页
     */
    private boolean isFirstPage;

    /**
     * 是否为最后一页
     */
    private boolean isLastPage;

    /**
     * 是否有上一页
     */
    private boolean hasPreviousPage;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;
}