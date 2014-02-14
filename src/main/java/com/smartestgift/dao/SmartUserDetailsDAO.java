package com.smartestgift.dao;

import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;

import java.util.UUID;

/**
 * Created by dikkini on 06.10.13
 * Email: dikkini@gmail.com
 */
public interface SmartUserDetailsDAO extends Repository<SmartUserDetails, String> {
    public SmartUserDetails findSmartUserDetailsByUserName(String login);
}
