package com.paymybill.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Component
public class GenericDAOImpl<T, PK extends Serializable> implements GenericDAO<T, PK> {

    @Autowired
    public SessionFactory sessionFactory;

    @Override
    public <T> T findOne(final Class<T> type, final PK id) {
        Session session = sessionFactory.getCurrentSession();
        T one = session.get(type, id);
        return one;
    }

    @Override
    public <T> List<T> findAll(final Class<T> type) {
        EntityManager em = sessionFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
        Root<T> fileRoot = criteriaQuery.from(type);
        criteriaQuery.select(fileRoot);
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Transactional
    @Override
    public <T> T create(T created) {
        Session session = sessionFactory.getCurrentSession();
        session.save(created);
        session.flush();
        return created;
    }

    @Transactional
    @Override
    public void delete(T deleted) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(deleted);
        session.flush();
    }

    @Transactional
    @Override
    public <T> T update(T updated) {
        Session session = sessionFactory.getCurrentSession();
        updated = (T) session.merge(updated);
        session.flush();
        return updated;
    }
}
