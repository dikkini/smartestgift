package com.smartestgift.security;

import com.smartestgift.dao.model.SmartUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dikkini on 2/8/14.
 * Email: dikkini@gmail.com
 */
public interface UserAuthProvider extends UserDetailsService {

    /**
     * Authenticate custom user from facebook, vkontakte and so on.
      */
    public void authenticateUser(SmartUserDetails smartUserDetails, HttpServletRequest request);
}
