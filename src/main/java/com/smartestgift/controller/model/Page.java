package com.smartestgift.controller.model;

import java.util.List;

/**
 * Created by dikkini on 16.05.14.
 * Email: dikkini@gmail.com
 */
public class Page {
    protected List<?> results;
    protected int pageNum;
    protected int pageSize;

    public List<?> getResults() {
        return results;
    }

    public void setResults(List<?> results) {
        this.results = results;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
