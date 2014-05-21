package com.smartestgift.controller.model;

import java.util.List;

/**
 * Created by dikkini on 21.05.14.
 * Email: dikkini@gmail.com
 */

public abstract class Page {
    protected List<?> results;
    protected int pageNum;
    protected int pageSize;
    protected boolean isNextPage = true;
    protected boolean isPreviousPage = false;

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

    public boolean isNextPage() {
        return isNextPage;
    }

    public void setNextPage(boolean isNextPage) {
        this.isNextPage = isNextPage;
    }

    public boolean isPreviousPage() {
        return isPreviousPage;
    }

    public void setPreviousPage(boolean isPreviousPage) {
        this.isPreviousPage = isPreviousPage;
    }
}