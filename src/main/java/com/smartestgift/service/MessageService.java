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
     * @param activeUser
     * @param conversation
     * @return
     */
    public List<Message> findMessagesInConversation(SmartUser activeUser, Conversation conversation);

    /**
     *
     * @param activeUser
     * @param conversation
     * @return
     */
    public List<Message> findNewMessagesInConversation(SmartUser activeUser, Conversation conversation);

    /**
     *
     * @param smartUser
     * @param message
     * @param conversationUuid
     */
    public void sendMessageToUser(SmartUser smartUser, String message, String conversationUuid);

    /**
     *
     * @param smartUser
     * @return
     */
    public Integer findCountUserUnreadMessages(SmartUser smartUser);
}
