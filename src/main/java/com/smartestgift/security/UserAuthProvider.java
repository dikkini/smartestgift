package com.smartestgift.security;

import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.SmartUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */

@Service
@Transactional(readOnly=true)
public class UserAuthProvider implements UserDetailsService {

    @Autowired
    private SmartUserDAO smartUserDAO;

    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        SmartUserDetails smartUserDetails = smartUserDAO.findSmartUserDetailsByUserName(login);
        return smartUserDetails;
    }
}