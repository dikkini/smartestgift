package com.smartestgift.service;

import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;

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
    public List<Message> findConversationMessages(SmartUser activeUser, Conversation conversation);

    /**
     *
     * @param smartUser
     * @param message
     * @param conversationUuid
     */
    public void sendMessageToUser(SmartUser smartUser, String message, String conversationUuid);

    /**
     *
     * @param smartUserFrom
     * @param usernameTo
     * @param message
     */
    public void createConversation(SmartUser smartUserFrom, String usernameTo, String message);

    /**
     *
     * @param smartUser
     * @return
     */
    public Integer findCountUserUnreadMessages(SmartUser smartUser);
}
