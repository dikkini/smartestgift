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
}
