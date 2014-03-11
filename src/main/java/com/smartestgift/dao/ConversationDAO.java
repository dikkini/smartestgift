package com.smartestgift.dao;

import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.Message;

import java.util.List;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */
public interface ConversationDAO {

    public List<Conversation> findAll();

    public void store(Conversation dmodel);

    public void delete(Conversation dmodel);
}