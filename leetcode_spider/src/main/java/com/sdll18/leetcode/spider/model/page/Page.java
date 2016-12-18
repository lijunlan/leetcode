package com.sdll18.leetcode.spider.model.page;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Copyright (C) 2015 - 2016 SOHU FOCUS Inc., All Rights Reserved.
 *
 * @Author: junlanli@sohu-inc.com
 * @Date: 2016-10-20
 */
public class Page<T> {

    private long total;

    private int pageStart;

    private int pageEnd;

    private List<T> rows;

    public Page() {
        pageStart = 0;
        pageEnd = 15;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public JSONObject toJSON() {
        JSONObject r = new JSONObject();
        r.put("list", rows);
        r.put("total", total);
        return r;
    }
}
