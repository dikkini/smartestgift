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
    public Integer findCountUnreadMessages(String username) {
        SmartUser smartUser = smartUserDAO.findSmartUserByUsername(username);
        MessageStatus messageStatus = messageStatusDAO.find(ApplicationConstants.MESSAGE_STATUS_NEW);
        List<Conversation> userConversations = conversationDAO.findConversationsByUser(smartUser);
        Integer countUserUnreadMessages = 0;
        for (Conversation conversation : userConversations) {
            countUserUnreadMessages += messageDAO.findMessagesByConversationAndStatusAndNotByUser(conversation,
                    messageStatus, smartUser).size();
        }
        return countUserUnreadMessages;
    }

    @Override
    public List<Message> findNewMessagesForUserByConversation(String username, String conversationUuid) {
        MessageStatus messageStatus = messageStatusDAO.find(ApplicationConstants.MESSAGE_STATUS_NEW);
        Conversation conversation = conversationDAO.find(conversationUuid);
        SmartUser smartUser = smartUserDAO.findSmartUserByUsername(username);
        List<Message> newMessages = messageDAO.findMessagesByConversationAndStatus(conversation, messageStatus);
        markMessageAsRead(newMessages, smartUser);
        return newMessages;
    }

    @Override
    public List<Message> findAllMessagesByConversationForUser(String username, String conversationUuid) {
        Conversation conversation = conversationDAO.find(conversationUuid);
        SmartUser smartUser = smartUserDAO.findSmartUserByUsername(username);
        List<Message> conversationMessages = messageDAO.findMessagesByConversation(conversation);
        markMessageAsRead(conversationMessages, smartUser);
        return conversationMessages;
    }


    private void markMessageAsRead(List<Message> messages, SmartUser smartUser) {
        MessageStatus messageStatusRead = messageStatusDAO.find(ApplicationConstants.MESSAGE_STATUS_READ);
        MessageStatus messageStatusNew = messageStatusDAO.find(ApplicationConstants.MESSAGE_STATUS_NEW);
        for (Message message : messages) {
            boolean userIsAuthor = message.getSmartUser().equals(smartUser);
            boolean messageIsNew = message.getMessageStatus().equals(messageStatusNew);
            if (!userIsAuthor && messageIsNew) {
                message.setMessageStatus(messageStatusRead);
                messageDAO.store(message);
            }
        }
    }
}
