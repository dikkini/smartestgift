package com.smartestgift.service;

import com.smartestgift.dao.ConversationDAO;
import com.smartestgift.dao.MessageDAO;
import com.smartestgift.dao.SmartUserDAO;
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

import java.util.Date;
import java.util.List;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SmartUserDAO smartUserDAO;

    @Autowired
    ConversationDAO conversationDAO;

    @Override
    public List<Message> findConversationMessages(Conversation conversation) {
        return messageDAO.findConversationMessages(conversation.getUuid());
    }

    @Override
    public void sendMessageToUser(String username, String message, String conversationUuid) {
        SmartUser smartUserByUsername = smartUserDAO.findSmartUserByUsername(username);
        Conversation conversation = conversationDAO.find(conversationUuid);

        Message msg = new Message();
        msg.setSmartUser(smartUserByUsername);
        msg.setDate(new Date());
        msg.setMessage(message);
        msg.setConversation(conversation);

        messageDAO.store(msg);
    }

    @Override
    public void createConversation(String usernameFrom, String usernameTo, String message) {
        Conversation conversation = new Conversation();
        conversation.setUser_from(smartUserDAO.findSmartUserByUsername(usernameFrom));
        conversation.setUser_to(smartUserDAO.findSmartUserByUsername(usernameTo));
        conversationDAO.store(conversation);
        sendMessageToUser(usernameFrom, message, conversation.getUuid());
    }

    @Override
    public Integer getCountUserMessages(String username) {
        return messageDAO.findCountUserMessages(username);
    }

}
