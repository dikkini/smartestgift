package com.smartestgift.service;

import com.restfb.types.User;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by dikkini on 18.02.14.
 * Email: dikkini@gmail.com
 */
public interface SmartUserService {
    /**
     *
     */
    public String authFacebookUser(User fbuser, HttpServletRequest request);

    /**
     *
     * @param login
     * @return
     */
    public boolean checkOccupiedUserLogin(String login);
}
