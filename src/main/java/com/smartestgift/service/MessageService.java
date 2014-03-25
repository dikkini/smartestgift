package com.smartestgift.service;

import com.smartestgift.dao.model.*;

import java.util.List;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */
public interface MessageService {

    /**
     *
     * @param smartUser
     * @param message
     * @param conversationUuid
     */
    public void sendMessageToUser(SmartUser smartUser, String message, String conversationUuid);

    /**
     * @param username
     * @return
     */
    public Integer findCountUnreadMessages(String username);

    /**
     *
     * @param username
     * @param conversationUuid
     * @return
     */
    public List<Message> findNewMessagesForUserByConversation(String username, String conversationUuid);

    /**
     *
     * @param username
     * @param conversationUuid
     * @return
     */
    public List<Message> findAllMessagesByConversationForUser(String username, String conversationUuid);
}
