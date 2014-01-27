package com.smartestgift.dao;

import com.smartestgift.dao.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: dikkini
 * Date: 10/6/13
 * Time: 2:06 PM
 */
public interface UserDAO extends Repository<User, Long> {
    public User findUserByUserName(String login);
}
