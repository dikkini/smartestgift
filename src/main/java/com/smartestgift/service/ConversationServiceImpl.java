package com.smartestgift.service;

import com.smartestgift.dao.ConversationDAO;
import com.smartestgift.dao.MessageDAO;
import com.smartestgift.dao.MessageStatusDAO;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.SmartUser;
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
    public Conversation createConversation(SmartUser smartUserFrom, SmartUser smartUserTo, String message) {
        Conversation conversation = new Conversation();
        conversation.setUser_from(smartUserFrom);
        conversation.setUser_to(smartUserTo);
        conversationDAO.store(conversation);
        messageService.sendMessageToUser(smartUserFrom, message, conversation.getUuid());
        return conversation;
    }

    @Override
    public List<Conversation> findUnreadConversationsByUsername(String username) {
        List<Conversation> conversationsWithUnreadMessages = new ArrayList<>();
        List<Conversation> all = conversationDAO.findAll();
        for (Conversation conversation : all) {
            List<Message> unreadMessagesByConversation = messageDAO.findUnreadMessagesByConversation(conversation.getUuid());
            if (unreadMessagesByConversation.size() > 0) {
                conversationsWithUnreadMessages.add(conversation);
            }
        }

        return conversationsWithUnreadMessages;
    }
}
