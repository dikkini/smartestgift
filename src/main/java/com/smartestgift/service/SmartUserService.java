package com.smartestgift.service;

import com.restfb.types.User;
import com.smartestgift.dao.model.File;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.dao.model.SmartUserFriend;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by dikkini on 18.02.14.
 * Email: dikkini@gmail.com
 */
public interface SmartUserService {

    /**
     * Create new user
     * @param username username
     * @param email email
     * @param passwordEncoded encoded password
     * @param firstName first name
     * @param lastName last name
     * @param authProviderId auth provider id (from ApplicationConstants)
     * @param roleId role id (from ApplicationConstants)
     * @return SmartUserDetails model
     */
    public SmartUserDetails createNewUser(String username, String email, String passwordEncoded, String firstName,
                                          String lastName, Integer authProviderId, Integer roleId);

    /**
     * Creating new user from facebook with new user data. New user data coming from page continue registration,
     * when username or email was occupied.
     * @param facebookUser facebook user model (restfb model
     * @param username username
     * @param email email
     * @param firstName first name
     * @param lastName last name
     * @param socialId social id
     * @return SmartUserDetails model
     */
    public SmartUserDetails createNewUserFromFacebook(User facebookUser, String username, String email, String firstName,
                                                      String lastName, String socialId);

    /**
     *
     * @param facebookUser
     * @return
     */
    public SmartUserDetails createNewUserFromFacebook(User facebookUser);


    /**
     *
     * @param socialId
     * @param providerId
     * @return
     */
    public SmartUserDetails findExistSocialUser(String socialId, Integer providerId);

    /**
     *
     * @param email
     * @return true - free
     */
    public boolean checkOccupiedEmail(String email);

    /**
     *
     * @param username
     * @return true - free
     */
    public boolean checkOccupiedUsername(String username);

    /**
     *
     * @param smartUserDetails
     * @param request
     */
    public void authenticateUser(SmartUserDetails smartUserDetails, HttpServletRequest request);

    /**
     *
     * @param smartUserDetails
     */
    public void checkUserAddress(SmartUserDetails smartUserDetails);

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

    /**
     *
     * @param activeUser
     * @param friendUsername
     */
    public void addRequestSmartUserFriend(SmartUser activeUser, String friendUsername);

    /**
     *
     * @param activeUser
     * @param friendUsername
     */
    public void removeSmartUserFriend(SmartUser activeUser, String friendUsername);

    /**
     *
     * @param activeUser
     * @return
     */
    public List<SmartUserFriend> findAllSmartUserFriends(SmartUser activeUser);

    /**
     *
     * @param activeUser
     * @param friendUuid
     * @param typeId
     */
    public void changeSmartUserFriendType(SmartUser activeUser, String friendUuid, int typeId);
}
