package com.smartestgift.service;

import com.restfb.types.User;
import com.smartestgift.dao.AuthProviderDAO;
import com.smartestgift.dao.RoleDAO;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.SmartUserDetailsDAO;
import com.smartestgift.dao.model.AuthProvider;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.security.UserAuthProvider;
import com.smartestgift.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dikkini on 18.02.14.
 * Email: dikkini@gmail.com
 */
@Service
public class SmartUserServiceImpl implements SmartUserService {

    @Autowired
    private AuthProviderDAO authProviderDAO;

    @Autowired
    private SmartUserDetailsDAO smartUserDetailsDAO;

    @Autowired
    private UserAuthProvider userAuthProvider;

    @Override
    public SmartUserDetails findExistSocialUser(String socialId, Integer providerId) {
        AuthProvider authProvider = authProviderDAO.find(providerId);
        return smartUserDetailsDAO.findUserBySocialIdAndAuthProvider(socialId, authProvider);
    }

    @Override
    public List<String> checkOccupiedEmailAndUsername(String username, String email) {
        List<String> result = new ArrayList<>();

        SmartUserDetails smartUserDetailsByEmail = smartUserDetailsDAO.findSmartUserDetailsByEmail(email);
        SmartUserDetails smartUserDetailsByUsername = smartUserDetailsDAO.findSmartUserDetailsByUsername(username);

        if (smartUserDetailsByEmail != null) {
            result.add("email");
        }

        if (smartUserDetailsByUsername != null) {
            result.add("username");
        }

        return result;
    }

    @Override
    public void createSmartUserDetails(SmartUserDetails smartUserDetails) {
        smartUserDetailsDAO.store(smartUserDetails);
    }

    @Override
    public boolean checkOccupiedUserLogin(String login) {
        SmartUserDetails userDetailsByUsername = smartUserDetailsDAO.findSmartUserDetailsByUsername(login);
        SmartUserDetails smartUserDetailsByEmail = smartUserDetailsDAO.findSmartUserDetailsByEmail(login);
        return userDetailsByUsername == null && smartUserDetailsByEmail == null;
    }

    @Override
    public void authenticateUser(SmartUserDetails smartUserDetails, HttpServletRequest request) {
        userAuthProvider.authenticateUser(smartUserDetails, request);
    }
}
