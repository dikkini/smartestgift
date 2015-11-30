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
        // TODO mark all messages as read
        this.markMessagesAsReadNotAuthor(conversationMessages, activeUser);
        return conversationMessages;
    }

    @Override
    public List<Message> findNewMessagesInConversation(String userName, String conversationUuid) {
        SmartUser smartUserByUsername = smartUserDAO.findByUsername(userName);
        Conversation conversation = conversationDAO.findOne(conversationUuid);
        MessageStatus messageStatus = messageStatusDAO.findOne(ApplicationConstants.MESSAGE_STATUS_NEW);
        List<Message> messagesByConversationAndStatus = messageDAO.findMessagesUserNotAuthorCountByConversationAndStatus
                (smartUserByUsername, conversation, messageStatus);
        this.markMessagesAsReadNotAuthor(messagesByConversationAndStatus, smartUserByUsername);
        return messagesByConversationAndStatus;
    }

    @Override
    public void sendMessageToUser(SmartUser smartUser, String message, String conversationUuid) {
        Conversation conversation = conversationDAO.findOne(conversationUuid);

        Message msg = new Message();
        msg.setSmartUser(smartUser);
        msg.setDate(new Date());
        msg.setMessage(message);
        msg.setConversation(conversation);
        msg.setMessageStatus(messageStatusDAO.findOne(ApplicationConstants.MESSAGE_STATUS_NEW));

        messageDAO.create(msg);
    }

    @Override
    public Integer findCountUserUnreadMessages(String username) {
        SmartUser smartUser = smartUserDAO.findByUsername(username);
        MessageStatus messageStatus = messageStatusDAO.findOne(ApplicationConstants.MESSAGE_STATUS_NEW);
        List<Conversation> userConversations = conversationDAO.findConversationsByUser(smartUser);
        Integer countUserUnreadMessages = 0;
        for (Conversation conversation : userConversations) {
            countUserUnreadMessages += messageDAO.findMessagesUserNotAuthorCountByConversationAndStatus(smartUser, conversation, messageStatus).size();
        }
        return countUserUnreadMessages;
    }

    private void markMessagesAsReadNotAuthor(List<Message> messages, SmartUser authorUser) {
        for (Message message : messages) {
            MessageStatus messageStatus = message.getMessageStatus();
            if (!message.getSmartUser().equals(authorUser) && messageStatus.getId().equals(ApplicationConstants.MESSAGE_STATUS_NEW)) {
                message.setMessageStatus(messageStatusDAO.findOne(ApplicationConstants.MESSAGE_STATUS_READ));
                messageDAO.create(message);
            }
        }
    }

}
