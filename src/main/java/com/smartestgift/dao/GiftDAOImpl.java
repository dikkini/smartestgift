package com.smartestgift.dao;

import com.smartestgift.dao.model.Gift;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dikkini on 22.02.14.
 * Email: dikkini@gmail.com
 */
@Service
@Transactional
public class GiftDAOImpl implements GiftDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Gift find(String id) {
        return (Gift) sessionFactory.getCurrentSession().get(Gift.class, id);
    }

    @Override
    public List<Gift> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Gift.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public void store(Gift dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
        session.refresh(dmodel);
    }

    @Override
    public void delete(Gift dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(dmodel);
        session.flush();
    }
}
