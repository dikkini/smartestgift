package com.smartestgift.dao;

import com.smartestgift.dao.model.GiftCategory;
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
public class GiftCategoryDAOImpl implements GiftCategoryDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public GiftCategory find(Integer id) {
        return (GiftCategory) sessionFactory.getCurrentSession().get(GiftCategory.class, id);
    }

    @Override
    public List<GiftCategory> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GiftCategory.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public void store(GiftCategory dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
        session.refresh(dmodel);
    }

    @Override
    public void delete(GiftCategory dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(dmodel);
        session.flush();
    }
}
