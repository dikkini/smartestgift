package com.smartestgift.service;

import com.restfb.types.User;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * Check user email and username
     * @param username
     * @param email
     * @return
     */
    public List<String> checkOccupiedEmailAndUsername(String username, String email);

    /**
     *
     * @param email
     * @return true - free
     */
    public boolean checkOccupiedEmail(String email);

    /**
     *
     * @param login
     * @return true - free
     */
    public boolean checkOccupiedUserLogin(String login);

    /**
     *
     * @param smartUserDetails
     * @param request
     */
    public void authenticateUser(SmartUserDetails smartUserDetails, HttpServletRequest request);
}
