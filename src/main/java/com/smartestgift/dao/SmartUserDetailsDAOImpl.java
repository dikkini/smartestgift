package com.smartestgift.dao;

import com.smartestgift.dao.model.AuthProvider;
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

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */
@Service
@Transactional
public class SmartUserDetailsDAOImpl implements SmartUserDetailsDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public SmartUserDetails find(String uuid) {
        return (SmartUserDetails) sessionFactory.getCurrentSession().get(SmartUserDetails.class, uuid);
    }

    @Override
    public List<SmartUserDetails> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUserDetails.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public void store(SmartUserDetails dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
        session.refresh(dmodel);
    }

    @Override
    public void delete(SmartUserDetails dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(dmodel);
        session.flush();
    }

    @Override
    public void merge(SmartUserDetails dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(dmodel);
        session.flush();
    }

    @Override
    public SmartUserDetails findSmartUserDetailsByEmail(String email) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUserDetails.class);
        criteria.add(Restrictions.eq("email", email));
        return (SmartUserDetails) criteria.uniqueResult();
    }

    @Override
    public SmartUserDetails findSmartUserDetailsByUsername(String username) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUserDetails.class);
        criteria.add(Restrictions.eq("username", username));
        return (SmartUserDetails) criteria.uniqueResult();
    }

    @Override
    public SmartUserDetails findUserBySocialIdAndAuthProvider(String socialId, AuthProvider facebookAuthProvider) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUserDetails.class);
        criteria.add(Restrictions.eq("socialId", socialId));
        criteria.add(Restrictions.eq("authProvider", facebookAuthProvider));
        return (SmartUserDetails) criteria.uniqueResult();
    }
}
