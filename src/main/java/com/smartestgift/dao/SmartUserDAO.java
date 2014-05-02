package com.smartestgift.dao;

import com.smartestgift.dao.Repository;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;

import java.util.List;

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

    /**
     *
     * @param username
     * @param activeUsername
     * @return
     */
    public List<SmartUser> findSmartUsersLikeUserName(String username, String activeUsername);

    /**
     *
     * @param firstname
     * @param activeUsername
     * @return
     */
    public List<SmartUser> findSmartUsersLikeFirstName(String firstname, String activeUsername);

    /**
     *
     * @param lastName
     * @param activeUsername
     * @return
     */
    public List<SmartUser> findSmartUsersLikeLastName(String lastName, String activeUsername);

    /**
     *
     * @param middleName
     * @param activeUsername
     * @return
     */
    public List<SmartUser> findSmartUsersLikeMiddleName(String middleName, String activeUsername);
}
