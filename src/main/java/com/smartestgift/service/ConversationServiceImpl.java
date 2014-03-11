package com.smartestgift.service;

import com.smartestgift.dao.ConversationDAO;
import com.smartestgift.dao.MessageDAO;
import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.SmartUser;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    ConversationDAO conversationDAO;

    @Override
    public List<Conversation> findUserConversations(SmartUser user) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Conversation.class);
        SimpleExpression smartUserFrom = Restrictions.eq("pk.userFrom", user);
        SimpleExpression smartUserTo = Restrictions.eq("pk.userTo", user);
        criteria.add(Restrictions.or(smartUserFrom, smartUserTo));
        return (List<Conversation>) criteria.list();
    }
}
