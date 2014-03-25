package com.smartestgift.dao;

import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.MessageStatus;
import com.smartestgift.dao.model.SmartUser;

import java.util.List;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */
public interface MessageDAO extends Repository<Message, String> {

    /**
     * @param conversation
     * @return
     */
    public List<Message> findMessagesByConversation(Conversation conversation);

    /**
     *
     * @param conversation
     * @param messageStatus
     * @return
     */
    public List<Message> findMessagesByConversationAndStatus(Conversation conversation, MessageStatus messageStatus);

    /**
     *
     * @param smartUser
     * @param conversation
     * @param messageStatus
     * @return
     */
    public List<Message> findMessagesByConversationAndStatusAndNotByUser(Conversation conversation,
                                                                         MessageStatus messageStatus, SmartUser smartUser);
}
