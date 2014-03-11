package com.smartestgift.service;

import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.SmartUser;

import java.util.List;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */
public interface MessageService {

    public List<Message> findUserConversations(SmartUser user);
}
