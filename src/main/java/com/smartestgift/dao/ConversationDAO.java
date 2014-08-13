package com.smartestgift.dao;

import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.SmartUser;

import java.util.List;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */
public interface ConversationDAO extends Repository<Conversation, String> {

    /**
     *
     * @param smartUser
     * @return
     */
    public List<Conversation> findConversationsByUser(SmartUser smartUser);
}
