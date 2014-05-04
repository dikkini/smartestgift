package com.smartestgift.service;

import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.SmartUser;

import java.util.List;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */
public interface ConversationService {

    /**
     *
     * @param uuid
     * @return
     */
    public Conversation findConversationByUuid(String uuid);

    /**
     *
     * @param user
     * @return
     */
    public List<Conversation> findConversationsByUser(SmartUser user);

    /**
     *
     * @param smartUserFrom
     * @param usernameTo
     * @param message
     */
    public Conversation createConversation(SmartUser smartUserFrom, String usernameTo, String message);

    /**
     *
     * @param username
     * @return
     */
    public List<Conversation> findUnreadConversationsByUsername(String username);
}
