package com.smartestgift.controller.model;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by dikkini on 14.05.14.
 * Email: dikkini@gmail.com
 */
public class Page {

    private List results;
    private int pageSize;
    private int page;

    public Page(){}

    public Page(Query query, int page, int pageSize) {

        this.page = page;
        this.pageSize = pageSize;
        results = query.setFirstResult(page * pageSize)
                .setMaxResults(pageSize + 1)
                .list();
    }

    public boolean isNextPage() {
        return results.size() > pageSize;
    }

    public boolean isPreviousPage() {
        return page > 0;
    }

    public List getList() {
        return isNextPage() ?
                results.subList(0, pageSize - 1) :
                results;
    }

}