package com.smartestgift.dao;

import com.smartestgift.dao.Repository;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;

/**
 * Created by dikkini on 06.10.13.
 * Email: dikkini@gmail.com
 */
public interface SmartUserDAO extends Repository<SmartUser, String> {

    /**
     * Поиск пользователя по username
     * @param username username
     * @return модель пользователя с деталями
     */
    public SmartUser findSmartUserByUsername(String username);
}
