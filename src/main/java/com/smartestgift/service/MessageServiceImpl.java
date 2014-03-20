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
    public List<Message> findMessagesInConversation(SmartUser activeUser, Conversation conversation) {
        List<Message> conversationMessages = messageDAO.findMessagesByConversation(conversation.getUuid());
        this.markMessagesAsReadNotAuthor(conversationMessages, activeUser);
        return conversationMessages;
    }

    @Override
    public List<Message> findNewMessagesInConversation(String userName, String conversationUuid) {
        SmartUser smartUserByUsername = smartUserDAO.findSmartUserByUsername(userName);
        Conversation conversation = conversationDAO.find(conversationUuid);
        MessageStatus messageStatus = messageStatusDAO.find(ApplicationConstants.MESSAGE_STATUS_NEW);
        List<Message> messagesByConversationAndStatus = messageDAO.findMessagesUserIsAuthorByConversationAndStatus
                (smartUserByUsername, conversation, messageStatus);
        this.markMessagesAsReadNotAuthor(messagesByConversationAndStatus, smartUserByUsername);
        return messagesByConversationAndStatus;
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
    public Integer findCountUserUnreadMessages(String username) {
        SmartUser smartUser = smartUserDAO.find(username);
        MessageStatus messageStatus = messageStatusDAO.find(ApplicationConstants.MESSAGE_STATUS_NEW);
        List<Conversation> userConversations = conversationDAO.findConversationsByUser(smartUser);
        Integer countUserUnreadMessages = 0;
        for (Conversation conversation : userConversations) {
            countUserUnreadMessages += messageDAO.findMessagesUserNotAuthorCountByConversationAndStatus(smartUser, conversation, messageStatus);
        }
        return countUserUnreadMessages;
    }

    private void markMessagesAsReadNotAuthor(List<Message> messages, SmartUser authorUser) {
        for (Message message : messages) {
            MessageStatus messageStatus = message.getMessageStatus();
            if (!message.getSmartUser().equals(authorUser) && messageStatus.getId().equals(ApplicationConstants.MESSAGE_STATUS_NEW)) {
                message.setMessageStatus(messageStatusDAO.find(ApplicationConstants.MESSAGE_STATUS_READ));
                messageDAO.store(message);
            }
        }
    }

}
