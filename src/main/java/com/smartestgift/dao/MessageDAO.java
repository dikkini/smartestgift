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
     * @param conversationUuid
     * @return
     */
    public List<Message> findMessagesByConversation(String conversationUuid);

    /**
     *
     * @param conversationUuid
     * @return
     */
    public List<Message> findUnreadMessagesByConversation(String conversationUuid);

    /**
     *
     * @param smartUser
     * @param conversation
     * @param messageStatus
     * @return
     */
    public List<Message> findMessagesUserNotAuthorCountByConversationAndStatus(SmartUser smartUser,
                                                                               Conversation conversation, MessageStatus messageStatus);
}
