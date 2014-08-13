package com.smartestgift.dao;

import com.smartestgift.dao.model.SmartUserGiftURL;
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
 * Created by dikkini on 30/06/14.
 */
@Service
@Transactional
public class SmartUserGiftURLDAOImpl implements SmartUserGiftURLDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public SmartUserGiftURL findOne(String id) {
        return (SmartUserGiftURL) sessionFactory.getCurrentSession().get(SmartUserGiftURL.class, id);
    }

    @Override
    public List<SmartUserGiftURL> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUserGiftURL.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public SmartUserGiftURL create(SmartUserGiftURL dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
        return dmodel;
    }

    @Override
    public void delete(SmartUserGiftURL dmodel) {

    }

    @Override
    public SmartUserGiftURL update(SmartUserGiftURL dmodel) {
        return dmodel;
    }

    @Override
    public SmartUserGiftURL findSmartUserGiftURLById(Integer id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUserGiftURL.class);
        criteria.add(Restrictions.eq("id", id));
        return (SmartUserGiftURL) criteria.uniqueResult();
    }

    @Override
    public SmartUserGiftURL findSmartUserGiftURLByShortUrl(String shortUrl) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUserGiftURL.class);
        criteria.add(Restrictions.eq("shortUrl", shortUrl));
        return (SmartUserGiftURL) criteria.uniqueResult();
    }
}
