package com.smartestgift.service;

import com.smartestgift.dao.ConversationDAO;
import com.smartestgift.dao.MessageDAO;
import com.smartestgift.dao.MessageStatusDAO;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.*;
import com.smartestgift.utils.ApplicationConstants;
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

    @Autowired
    MessageStatusDAO messageStatusDAO;

    @Override
    public List<Message> findConversationMessages(SmartUser activeUser, Conversation conversation) {
        List<Message> conversationMessages = messageDAO.findConversationMessages(conversation.getUuid());
        for (Message conversationMsg : conversationMessages) {
            MessageStatus messageStatus = conversationMsg.getMessageStatus();
            if (!conversationMsg.getSmartUser().equals(activeUser) && messageStatus.getId().equals(ApplicationConstants.MESSAGE_STATUS_NEW)) {
                conversationMsg.setMessageStatus(messageStatusDAO.find(ApplicationConstants.MESSAGE_STATUS_READ));
                messageDAO.store(conversationMsg);
            }
        }

        return conversationMessages;
    }

    @Override
    public void sendMessageToUser(SmartUser smartUser, String message, String conversationUuid) {
        Conversation conversation = conversationDAO.find(conversationUuid);

        Message msg = new Message();
        msg.setSmartUser(smartUser);
        msg.setDate(new Date());
        msg.setMessage(message);
        msg.setConversation(conversation);
        msg.setMessageStatus(messageStatusDAO.find(ApplicationConstants.MESSAGE_STATUS_NEW));

        messageDAO.store(msg);
    }

    @Override
    public void createConversation(SmartUser smartUserFrom, String usernameTo, String message) {
        Conversation conversation = new Conversation();
        conversation.setUser_from(smartUserFrom);
        conversation.setUser_to(smartUserDAO.findSmartUserByUsername(usernameTo));
        conversationDAO.store(conversation);
        sendMessageToUser(smartUserFrom, message, conversation.getUuid());
    }

    @Override
    public Integer findCountUserUnreadMessages(SmartUser smartUser) {
        MessageStatus messageStatus = messageStatusDAO.find(ApplicationConstants.MESSAGE_STATUS_NEW);
        List<Conversation> userConversations = conversationDAO.findUserConversations(smartUser);
        Integer countUserUnreadMessages = 0;
        for (Conversation conversation : userConversations) {
            countUserUnreadMessages += messageDAO.findMessagesUserNotAuthorCountByConversationAndStatus(smartUser, conversation, messageStatus);
        }
        return countUserUnreadMessages;
    }

}
