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
     * @param conversation
     * @return
     */
    public List<Message> findConversationMessages(Conversation conversation);

    /**
     *
     * @param username
     * @param message
     * @param conversationUuid
     */
    public void sendMessageToUser(String username, String message, String conversationUuid);

    /**
     *
     * @param usernameFrom
     * @param usernameTo
     * @param message
     */
    public void createConversation(String usernameFrom, String usernameTo, String message);

    /**
     *
     * @param username
     * @return
     */
    public Integer getCountUserMessages(String username);
}
