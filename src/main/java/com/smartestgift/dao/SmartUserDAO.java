package com.smartestgift.dao;

import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * SmartUser: dikkini
 * Date: 10/6/13
 * Time: 2:06 PM
 */
public interface SmartUserDAO extends Repository<SmartUser, UUID> {
    public SmartUserDetails findSmartUserDetailsByLogin(String login);
}
