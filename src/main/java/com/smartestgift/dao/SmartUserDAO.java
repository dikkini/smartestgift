package com.smartestgift.dao;

import com.smartestgift.dao.Repository;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;

/**
 * Created by dikkini on 06.10.13.
 * Email: dikkini@gmail.com
 */
public interface SmartUserDAO extends Repository<SmartUser, String> {
    public SmartUser findSmartUserByUserName(String login);
}
