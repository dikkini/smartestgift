package com.smartestgift.service;

import com.restfb.types.User;
import com.smartestgift.dao.AuthProviderDAO;
import com.smartestgift.dao.RoleDAO;
import com.smartestgift.dao.SmartUserDetailsDAO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.security.UserAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by dikkini on 18.02.14.
 * Email: dikkini@gmail.com
 */
@Service
public class SmartUserServiceImpl implements SmartUserService {

    @Autowired
    private UserAuthProvider userAuthProvider;

    @Autowired
    private AuthProviderDAO authProviderDAO;

    @Autowired
    private SmartUserDetailsDAO smartUserDetailsDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public String authFacebookUser(User facebookUser, HttpServletRequest request) {
        boolean emailOccupied = false;
        boolean usernameOccupied = false;

        SmartUserDetails existFacebookUser = smartUserDetailsDAO.findUserBySocialIdAndAuthProvider(facebookUser.getId(), authProviderDAO.findFacebookProvider());

        // create new user
        if (existFacebookUser == null) {
            SmartUser authSmartUser = new SmartUser(facebookUser.getBirthdayAsDate(), facebookUser.getUsername(), facebookUser.getFirstName(),
                    facebookUser.getLastName(), facebookUser.getMiddleName());
            authSmartUser.setAddress(facebookUser.getHometownName());

            existFacebookUser = new SmartUserDetails(authSmartUser, null, facebookUser.getEmail(), facebookUser.getId(), new Date(),
                    roleDAO.findUserRole(), authProviderDAO.findFacebookProvider());

            SmartUserDetails smartUserDetailsByEmail = smartUserDetailsDAO.findSmartUserDetailsByEmail(facebookUser.getEmail());
            SmartUserDetails smartUserDetailsByUsername = smartUserDetailsDAO.findSmartUserDetailsByUsername(facebookUser.getUsername());

            if (smartUserDetailsByEmail != null) {
                emailOccupied = true;
            }
            if (smartUserDetailsByUsername != null) {
                usernameOccupied = true;
            }

            if (emailOccupied || usernameOccupied) {
                request.getSession().setAttribute(facebookUser.getId(), existFacebookUser);
                return "redirect:signup/social?id=" + facebookUser.getId() +"&errors=" + (emailOccupied ? "" : "email") + (usernameOccupied ? "" : ",username");
            }
        }

        userAuthProvider.authenticateUser(existFacebookUser, request);
        return "redirect:profile";
    }
}
