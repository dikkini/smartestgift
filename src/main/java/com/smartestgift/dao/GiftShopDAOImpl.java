package com.smartestgift.dao;

import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.GiftShop;
import com.smartestgift.dao.model.Shop;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dikkini on 28/05/14.
 */

@Service
@Transactional
public class GiftShopDAOImpl implements GiftShopDAO {

    @Autowired
    SessionFactory sessionFactory;


    @Override
    public GiftShop findOne(String id) {
        return (GiftShop) sessionFactory.getCurrentSession().get(GiftShop.class, id);
    }

    @Override
    public List<GiftShop> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GiftShop.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public GiftShop create(GiftShop dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
        session.refresh(dmodel);
        return dmodel;
    }

    @Override
    public void delete(GiftShop dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(dmodel);
        session.flush();
    }

    @Override
    public GiftShop update(GiftShop dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(dmodel);
        session.flush();
        return dmodel;
    }

    @Override
    public List<GiftShop> findGiftShopsByGiftUuid(Gift gift) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GiftShop.class);
        criteria.add(Restrictions.eq("gift", gift));
        return (List<GiftShop>) criteria.list();
    }

    @Override
    public GiftShop findGiftShopByGiftAndShop(Gift gift, Shop shop) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GiftShop.class);
        criteria.add(Restrictions.eq("gift", gift));
        criteria.add(Restrictions.eq("shop", shop));
        return null;
    }
}
