package com.smartestgift.service;

import com.restfb.types.User;
import com.smartestgift.dao.*;
import com.smartestgift.dao.model.*;
import com.smartestgift.security.UserAuthProvider;
import com.smartestgift.utils.ApplicationConstants;
import com.smartestgift.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.smartestgift.utils.ApplicationConstants.*;

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
    private SmartUserDAO smartUserDAO;

    @Autowired
    private UserAuthProvider userAuthProvider;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private FileDAO fileDAO;

    @Override
    public SmartUserDetails createNewUser(String username, String email, String passwordEncoded, String firstName, String lastName, Integer authProviderId, Integer roleId) {

        SmartUser smartUser = new SmartUser();
        smartUser.setUsername(username);
        smartUser.setFirstName(firstName);
        smartUser.setLastName(lastName);
        smartUser.setFile(fileDAO.find(FILE_USER_NO_PHOTO_ID));

        Role role = roleDAO.find(roleId);
        AuthProvider authProvider = authProviderDAO.find(authProviderId);

        SmartUserDetails smartUserDetails = new SmartUserDetails();
        smartUserDetails.setSmartUser(smartUser);
        smartUserDetails.setUsername(username);
        smartUserDetails.setEmail(email);
        smartUserDetails.setPassword(passwordEncoded);
        smartUserDetails.setRegistrationDate(new Date());
        smartUserDetails.setAuthProvider(authProvider);
        smartUserDetails.setRole(role);

        smartUserDetailsDAO.store(smartUserDetails);

        return smartUserDetails;
    }

    @Override
    public SmartUserDetails createNewUserFromFacebook(User facebookUser, String username, String email, String firstName, String lastName, String socialId) {
        SmartUserDetails smartUserDetails = getSmartUserDetailsFromFacebookUser(facebookUser);
        SmartUser smartUser = smartUserDetails.getSmartUser();
        smartUser.setUsername(username);
        smartUser.setLastName(lastName);
        smartUser.setFirstName(firstName);
        smartUser.setFile(fileDAO.find(FILE_USER_NO_PHOTO_ID));

        smartUserDetails.setSocialId(socialId);
        return smartUserDetails;
    }

    @Override
    public SmartUserDetails createNewUserFromFacebook(User facebookUser) {
        return getSmartUserDetailsFromFacebookUser(facebookUser);
    }

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
    public boolean checkOccupiedUserLogin(String login) {
        SmartUserDetails userDetailsByUsername = smartUserDetailsDAO.findSmartUserDetailsByUsername(login);
        SmartUserDetails smartUserDetailsByEmail = smartUserDetailsDAO.findSmartUserDetailsByEmail(login);
        return userDetailsByUsername == null && smartUserDetailsByEmail == null;
    }

    @Override
    public boolean checkOccupiedEmail(String email) {
        SmartUserDetails smartUserDetailsByEmail = smartUserDetailsDAO.findSmartUserDetailsByEmail(email);
        return smartUserDetailsByEmail == null;
    }

    @Override
    public void authenticateUser(SmartUserDetails smartUserDetails, HttpServletRequest request) {
        userAuthProvider.authenticateUser(smartUserDetails, request);
    }

    @Override
    public void checkUserAddress(SmartUserDetails smartUserDetails) {

    }

    private SmartUserDetails getSmartUserDetailsFromFacebookUser(User facebookUser) {
        SmartUser smartUser = new SmartUser();
        smartUser.setUsername(facebookUser.getUsername());
        smartUser.setLastName(facebookUser.getLastName());
        smartUser.setFirstName(facebookUser.getFirstName());
        smartUser.setMiddleName(facebookUser.getMiddleName());
        smartUser.setBirthDate(facebookUser.getBirthdayAsDate());
        smartUser.setAddress(facebookUser.getHometownName());
        // TODO photo take from facebook
        smartUser.setFile(fileDAO.find(FILE_USER_NO_PHOTO_ID));
        // TODO determine gender and fill it
        //smartUser.setGender(facebookUser.getGender());

        SmartUserDetails smartUserDetails = new SmartUserDetails();
        smartUserDetails.setSmartUser(smartUser);
        smartUserDetails.setRole(roleDAO.find(USER_ROLE_ID));
        smartUserDetails.setAuthProvider(authProviderDAO.find(FACEBOOK_AUTH_PROVIDER_ID));
        smartUserDetails.setEmail(facebookUser.getEmail());
        smartUserDetails.setSocialId(facebookUser.getId());
        smartUserDetails.setRegistrationDate(new Date());
        return smartUserDetails;
    }
}
