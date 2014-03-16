package com.smartestgift.dao;

import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.MessageStatus;
import com.smartestgift.dao.model.SmartUser;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Order;
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

    @Override
    public List<Message> findConversationMessages(String conversationUuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class);
        criteria.add(Restrictions.eq("conversation.uuid", conversationUuid));
        criteria.addOrder(org.hibernate.criterion.Order.asc("date"));
        return (List<Message>) criteria.list();
    }

    @Override
    public List<Message> findUserMessagesByStatus(SmartUser smartUser, MessageStatus messageStatus) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class);
        criteria.add(Restrictions.eq("smartUser", smartUser));
        criteria.add(Restrictions.eq("messageStatus", messageStatus));
        return (List<Message>) criteria.list();
    }

    @Override
    public Integer findMessagesUserNotAuthorCountByConversationAndStatus(SmartUser smartUser, Conversation conversation, MessageStatus messageStatus) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class);
        criteria.add(Restrictions.eq("messageStatus", messageStatus));
        criteria.add(Restrictions.eq("conversation", conversation));
        criteria.add(Restrictions.ne("smartUser", smartUser));
        return ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }
}
