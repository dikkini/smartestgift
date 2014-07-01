package com.smartestgift.service;

import com.smartestgift.dao.model.File;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserFriend;
import com.smartestgift.dao.model.SmartUserGiftURL;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dikkini on 18.02.14.
 * Email: dikkini@gmail.com
 */
public interface SmartUserService {

    /**
     *
     * @param username
     * @return
     */
    public SmartUser findUserByUsername(String username);

    /**
     *
     * @param username
     * @param password
     * @param email
     * @param lastName
     * @param firstName
     * @param authProvider
     * @param enabled
     * @return
     */
    public SmartUser createSmartUser(String username, String password, String email, String lastName, String firstName,
                                    Date registrationDate,  int authProvider, boolean enabled);

    /**
     *
     * @param smartUser
     * @return
     */
    public SmartUser createSmartUser(SmartUser smartUser);

    /**
     *
     * @param socialId
     * @param providerId
     * @return
     */
    public SmartUser findExistSocialUser(String socialId, Integer providerId);

    /**
     *
     * @param email
     * @return true - free
     */
    public boolean isEmailBusy(String email);

    /**
     *
     * @param username
     */
    public void createUserAuthorityForUser(String username);

    /**
     *
     * @param username
     * @return true - free
     */
    public boolean isUsernameBusy(String username);

    /**
     *
     * @param userName
     * @param password
     * @param request
     */
    public void authenticateUser(String userName, String password, HttpServletRequest request);

    /**
     *
     * @param smartUser
     */
    public void checkUserAddress(SmartUser smartUser);

    /**
     *
     * @param name
     * @param activeUser
     * @return
     */
    public List<SmartUser> findUsersByUserInput(String name, SmartUser activeUser);

    /**
     *
     * @param searchString
     * @param activeUser
     * @return
     */
    public Map<String, List> findUsersAndGiftsByUserInput(String searchString, SmartUser activeUser);

    /**
     *
     * @param offset
     * @param smartUser
     * @return
     */
    public List<SmartUser> findUsersWithOffset(int offset, SmartUser smartUser);

    /**
     *
     * @param smartUser
     * @param file
     */
    public void updateUserFile(SmartUser smartUser, File file);
}
