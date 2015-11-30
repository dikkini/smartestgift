package com.smartestgift.dao;

import com.smartestgift.dao.model.MessageStatus;
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
public class MessageStatusDAOImpl implements MessageStatusDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public MessageStatus findOne(Integer id) {
        return (MessageStatus) sessionFactory.getCurrentSession().get(MessageStatus.class, id);
    }

    @Override
    public List<MessageStatus> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MessageStatus.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public MessageStatus create(MessageStatus dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dmodel);
        session.flush();
        return dmodel;
    }

    @Override
    public void delete(MessageStatus dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(dmodel);
        session.flush();
    }

    @Override
    public MessageStatus update(MessageStatus dmodel) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(dmodel);
        session.flush();
        return dmodel;
    }
}
