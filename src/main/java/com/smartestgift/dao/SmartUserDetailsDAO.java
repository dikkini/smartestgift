package com.smartestgift.dao;

import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;

import java.util.UUID;

/**
 * Created by dikkini on 06.10.13
 * Email: dikkini@gmail.com
 */
public interface SmartUserDetailsDAO extends Repository<SmartUserDetails, String> {
    /**
     *
     * @param socialId
     * @return
     */
    public SmartUserDetails findFacebookUserBySocialId(String socialId);

    /**
     * Поиск пользователя по email
     * @param email email
     * @return модель пользователя с деталями
     */
    public SmartUserDetails findSmartUserDetailsByEmail(String email);

    /**
     * Поиск пользователя по username
     * @param username username
     * @return модель пользователя с деталями
     */
    public SmartUserDetails findSmartUserDetailsByUsername(String username);
}
