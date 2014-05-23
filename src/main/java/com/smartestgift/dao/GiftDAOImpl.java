package com.smartestgift.dao;

import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.GiftCategory;
import com.smartestgift.dao.model.SmartUser;
import org.hibernate.*;
import org.hibernate.criterion.*;
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

    @Override
    public void merge(Gift dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(dmodel);
        session.flush();
    }

    @Override
    public List<Gift> findGiftsLimitSizeByCategory(int offset, int count, GiftCategory category) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Gift.class);
        criteria.setFirstResult(offset);
        criteria.setMaxResults(count);
        criteria.add(Restrictions.eq("category", category));
        criteria.addOrder(org.hibernate.criterion.Order.asc("name"));
        return criteria.list();
    }

    @Override
    public List<Gift> findGiftsLimitSizeBySearchString(int offset, int count, String searchString) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Gift.class);
        criteria.setFirstResult(offset);
        criteria.setMaxResults(count);
        Criterion nameGiftRestriction = Restrictions.ilike("name", searchString, MatchMode.ANYWHERE);
        Criterion descriptionGiftRestriction= Restrictions.ilike("description", searchString, MatchMode.ANYWHERE);
        criteria.add(Restrictions.or(nameGiftRestriction, descriptionGiftRestriction));
        criteria.addOrder(org.hibernate.criterion.Order.asc("name"));
        return criteria.list();
    }

    @Override
    public Long findCountAllGiftsByCategoryCode(String categoryCode) {
        return (Long) sessionFactory.getCurrentSession().createCriteria(Gift.class)
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long findCountAllGiftsBySearchString(String searchString) {
        return (Long) sessionFactory.getCurrentSession().createCriteria(Gift.class)
                .setProjection(Projections.rowCount()).uniqueResult();
    }
}
