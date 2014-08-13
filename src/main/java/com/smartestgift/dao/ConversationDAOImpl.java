package com.smartestgift.dao;

import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.SmartUser;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */

@Service
@Transactional
public class ConversationDAOImpl implements ConversationDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Conversation findOne(String id) {
        return (Conversation) sessionFactory.getCurrentSession().get(Conversation.class, id);
    }

    @Override
    public List<Conversation> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Conversation.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public Conversation create(Conversation dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
        return dmodel;
    }

    @Override
    public void delete(Conversation dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(dmodel);
        session.flush();
    }

    @Override
    public Conversation update(Conversation dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(dmodel);
        session.flush();
        return dmodel;
    }

    @Override
    public List<Conversation> findConversationsByUser(SmartUser smartUser) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Conversation.class);
        SimpleExpression smartUserFrom = Restrictions.eq("user_from", smartUser);
        SimpleExpression smartUserTo = Restrictions.eq("user_to", smartUser);
        criteria.add(Restrictions.or(smartUserFrom, smartUserTo));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return (List<Conversation>) criteria.list();
    }
}
