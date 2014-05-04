package com.smartestgift.dao;

import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
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
public class SmartUserDAOImpl implements SmartUserDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public SmartUser find(String uuid) {
        return (SmartUser) sessionFactory.getCurrentSession().get(SmartUser.class, uuid);
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
    }

    @Override
    public void delete(SmartUser dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(dmodel);
        session.flush();
    }

    @Override
    public void merge(SmartUser dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(dmodel);
        session.flush();
    }

    @Override
    public SmartUser findSmartUserByUsername(String username) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUser.class);
        criteria.add(Restrictions.eq("username", username));
        return (SmartUser) criteria.uniqueResult();
    }

    @Override
    public List<SmartUser> findSmartUsersLikeUserName(String username, String activeUsername) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUser.class);
        Criterion userLikeCriteria = Restrictions.ilike("username", username, MatchMode.ANYWHERE);
        Criterion userNeActive = Restrictions.ne("username", activeUsername);
        criteria.add(Restrictions.and(userLikeCriteria, userNeActive));
        return criteria.list();
    }

    @Override
    public List<SmartUser> findSmartUsersLikeFirstName(String firstName, String activeFistName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUser.class);
        Criterion firstNameLikeCriteria = Restrictions.ilike("firstName", firstName, MatchMode.ANYWHERE);
        Criterion firstNameNeActive = Restrictions.ne("firstName", activeFistName);
        criteria.add(Restrictions.and(firstNameLikeCriteria, firstNameNeActive));
        return criteria.list();
    }

    @Override
    public List<SmartUser> findSmartUsersLikeLastName(String lastName, String activeLastName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUser.class);
        Criterion lastNameLikeCriteria = Restrictions.ilike("lastName", lastName, MatchMode.ANYWHERE);
        Criterion lastNameNeActive = Restrictions.ne("lastName", activeLastName);
        criteria.add(Restrictions.and(lastNameLikeCriteria, lastNameNeActive));
        return criteria.list();
    }

    @Override
    public List<SmartUser> findSmartUsersLikeMiddleName(String middleName, String activeMiddleName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmartUser.class);
        Criterion middleNameLikeCriteria = Restrictions.ilike("middleName", middleName, MatchMode.ANYWHERE);
        Criterion middleNameNeActive = Restrictions.ne("middleName", activeMiddleName);
        criteria.add(Restrictions.and(middleNameLikeCriteria, middleNameNeActive));
        return criteria.list();
    }
}
