package com.smartestgift.service;

import com.smartestgift.dao.ConversationDAO;
import com.smartestgift.dao.MessageDAO;
import com.smartestgift.dao.MessageStatusDAO;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.MessageStatus;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.utils.ApplicationConstants;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<Conversation> findUnreadConversationsByUsername(String username) {
        SmartUser smartUserByUsername = smartUserDAO.findSmartUserByUsername(username);
        MessageStatus messageStatus = messageStatusDAO.find(ApplicationConstants.MESSAGE_STATUS_NEW);
        List<Conversation> conversationsWithUnreadMessages = new ArrayList<>();
        List<Conversation> all = conversationDAO.findAll();
        for (Conversation conversation : all) {
            List<Message> unreadMessagesByConversation = messageDAO.findMessagesByConversationAndStatus(conversation,
                    messageStatus);
            if (unreadMessagesByConversation.size() > 0 && !conversation.getUser_from().equals(smartUserByUsername)) {
                conversationsWithUnreadMessages.add(conversation);
            }
        }

        return conversationsWithUnreadMessages;
    }
}
