package com.smartestgift.service;

import com.smartestgift.dao.ConversationDAO;
import com.smartestgift.dao.MessageDAO;
import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.SmartUser;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDAO messageDAO;

    @Override
    public List<Message> findConversationMessages(Conversation conversation) {
        return messageDAO.findConversationMessages(conversation.getUuid());
    }
}
