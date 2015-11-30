package com.smartestgift.service;

import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.SmartUser;

import java.util.List;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */
public interface MessageService {
    /**
     *
     * @param activeUser
     * @param conversation
     * @return
     */
    public List<Message> findMessagesInConversation(SmartUser activeUser, Conversation conversation);

    /**
     *
     * @param userName
     * @param conversationUuid
     * @return
     */
    public List<Message> findNewMessagesInConversation(String userName, String conversationUuid);

    /**
     *
     * @param smartUser
     * @param message
     * @param conversationUuid
     */
    public void sendMessageToUser(SmartUser smartUser, String message, String conversationUuid);

    /**
     *
     * @param username
     * @return
     */
    public Integer findCountUserUnreadMessages(String username);
}
