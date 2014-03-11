package com.smartestgift.dao;

import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.SmartUser;

import java.util.List;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */
public interface MessageDAO extends Repository<Message, String> {

    public List<Message> findAllUserMessages(String userUuid);
}
