package com.smartestgift.dao;

import com.smartestgift.dao.model.Message;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
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
public class MessageDAOImpl implements MessageDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Message find(String uuid) {
        return (Message) sessionFactory.getCurrentSession().get(Message.class, uuid);
    }

    @Override
    public List<Message> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public void store(Message dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
    }

    @Override
    public void delete(Message dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(dmodel);
        session.flush();
    }

    @Override
    public void merge(Message dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(dmodel);
        session.flush();
    }
}
