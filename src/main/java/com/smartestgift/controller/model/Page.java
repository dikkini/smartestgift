package com.smartestgift.controller.model;

import org.hibernate.Criteria;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by dikkini on 14.05.14.
 * Email: dikkini@gmail.com
 */
public class Page {

    private Session session;
    private Class cls;
    private List results;
    private int pageSize;
    private int page;

    public Page() {}

    public Page(Session session, int page, int pageSize, Class cls) {
        this.page = page;
        this.pageSize = pageSize;
        this.session = session;
        this.cls = cls;

        Criteria criteria = session.createCriteria(cls);
        criteria.setFirstResult(page * pageSize);
        criteria.setMaxResults(pageSize + 1);
        results = criteria.list();
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

    public List getNew() {
        if (isNextPage()) {
            page += 1;
            Criteria criteria = session.createCriteria(cls);
            criteria.setFirstResult(page * pageSize);
            criteria.setMaxResults(pageSize + 1);
            results = criteria.list();
            return results;
        } else {
            return null;
        }
    }

    public List getOld() {
        if (isPreviousPage()) {
            page -= 1;
            Criteria criteria = session.createCriteria(cls);
            criteria.setFirstResult(page * pageSize);
            criteria.setMaxResults(pageSize + 1);
            results = criteria.list();
            return results;
        } else {
            return null;
        }
    }

    public List getResults() {
        return results;
    }
}