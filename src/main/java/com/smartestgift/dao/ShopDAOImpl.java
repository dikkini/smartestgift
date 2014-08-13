package com.smartestgift.dao;

import com.smartestgift.dao.model.Shop;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dikkini on 28/05/14.
 */

@Service
@Transactional
public class ShopDAOImpl implements ShopDAO {

    @Autowired
    SessionFactory sessionFactory;


    @Override
    public Shop findOne(String id) {
        return (Shop) sessionFactory.getCurrentSession().get(Shop.class, id);
    }

    @Override
    public List<Shop> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Shop.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public Shop create(Shop dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
        session.refresh(dmodel);
        return dmodel;
    }

    @Override
    public void delete(Shop dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(dmodel);
        session.flush();
    }

    @Override
    public Shop update(Shop dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(dmodel);
        session.flush();
        return dmodel;
    }
}
