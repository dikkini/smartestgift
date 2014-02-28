package com.smartestgift.service;

import com.restfb.types.User;
import com.smartestgift.dao.model.SmartUserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by dikkini on 18.02.14.
 * Email: dikkini@gmail.com
 */
public interface SmartUserService {

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
     * Creating user
     */
    public void createSmartUserDetails(SmartUserDetails smartUserDetails);

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
