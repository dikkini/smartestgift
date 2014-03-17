package com.smartestgift.service;

import com.smartestgift.dao.ConversationDAO;
import com.smartestgift.dao.MessageDAO;
import com.smartestgift.dao.MessageStatusDAO;
import com.smartestgift.dao.SmartUserDAO;
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
    MessageDAO messageDAO;

    @Autowired
    SmartUserDAO smartUserDAO;

    @Autowired
    ConversationDAO conversationDAO;

    @Autowired
    MessageStatusDAO messageStatusDAO;

    @Autowired
    MessageService messageService;

    @Override
    public Conversation findConversationByUuid(String uuid) {
        return conversationDAO.find(uuid);
    }

    @Override
    public List<Conversation> findConversationsByUser(SmartUser user) {
        return conversationDAO.findConversationsByUser(user);
    }

    @Override
    public void createConversation(SmartUser smartUserFrom, String usernameTo, String message) {
        Conversation conversation = new Conversation();
        conversation.setUser_from(smartUserFrom);
        conversation.setUser_to(smartUserDAO.findSmartUserByUsername(usernameTo));
        conversationDAO.store(conversation);
        messageService.sendMessageToUser(smartUserFrom, message, conversation.getUuid());
    }
}
