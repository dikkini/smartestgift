package com.smartestgift.dao;

import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */
@Service
@Transactional
public class SmartUserDAOImpl implements SmartUserDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public SmartUser find(UUID id) {
        return (SmartUser) sessionFactory.getCurrentSession().get(SmartUser.class, id);
    }

    @Override
    public List<SmartUser> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUser.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public void store(SmartUser dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
        session.refresh(dmodel);
    }

    @Override
    public void delete(SmartUser dmodel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SmartUserDetails findSmartUserDetailsByLogin(String login) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUserDetails.class);
        criteria.add(Restrictions.eq("login", login));
        return (SmartUserDetails) criteria.uniqueResult();
    }
}
