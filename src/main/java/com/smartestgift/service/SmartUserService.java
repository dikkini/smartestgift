package com.smartestgift.service;

import com.smartestgift.controller.model.RegisterSmartUserDTO;
import com.smartestgift.dao.model.SmartUser;

import javax.servlet.http.HttpServletRequest;
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
    public SmartUser findByUsername(String username);

    /**
     *
     * @param createdDTO
     * @return
     */
    public SmartUser create(RegisterSmartUserDTO createdDTO);

    /**
     *
     * @param smartUser
     * @return
     */
    public SmartUser create(SmartUser smartUser);

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
    public void createUserAuthority(String username);

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
     * @param name
     * @param activeUser
     * @return
     */
    public List<SmartUser> findByUserInput(String name, SmartUser activeUser);

    // TODO вынести в другое API
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
     * @param activeUser
     * @return
     */
    public List<SmartUser> findWithOffset(int offset, SmartUser activeUser);

    /**
     *
     * @param smartUser
     */
    public void update(SmartUser smartUser);

    /**
     *
     * @param updatedDTO
     */
    public void update(RegisterSmartUserDTO updatedDTO);
}
