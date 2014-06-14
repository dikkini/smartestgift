package com.smartestgift.service;

import com.google.gson.Gson;
import com.restfb.types.User;
import com.smartestgift.dao.*;
import com.smartestgift.dao.model.*;
import com.smartestgift.security.UserAuthProvider;
import com.smartestgift.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    private GiftDAO giftDAO;

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

        smartUser.setSmartUserDetails(smartUserDetails);

        smartUserDAO.store(smartUser);

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
    public boolean checkOccupiedUsername(String username) {
        SmartUserDetails userDetailsByUsername = smartUserDetailsDAO.findSmartUserDetailsByUsername(username);
        return userDetailsByUsername == null;
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
        // TODO do it
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

    @Override
    public List<SmartUser> findUsersByUserInput(String name, SmartUser activeUser) {
        List<SmartUser> resultList = new ArrayList<>();
        String[] split = name.split(" ");
        for (String str : split) {
            if (str.equals("")) {
                continue;
            }
            resultList.addAll(smartUserDAO.findSmartUsersLikeUserName(str, activeUser.getUsername()));
            resultList.addAll(smartUserDAO.findSmartUsersLikeLastName(str, activeUser.getLastName()));
            resultList.addAll(smartUserDAO.findSmartUsersLikeFirstName(str, activeUser.getFirstName()));
            resultList.addAll(smartUserDAO.findSmartUsersLikeMiddleName(str, activeUser.getMiddleName()));
        }

        return removeDuplicates(resultList);
    }

    // TODO вынести метод в api (?)
    @Override
    public Map<String, List> findUsersAndGiftsByUserInput(String searchString, SmartUser activeUser) {
        List<SmartUser> usersByUserInput = this.findUsersByUserInput(searchString, activeUser);
        List<Gift> giftsBySearchString = giftDAO.findGiftsBySearchString(searchString);
        Map<String, List> result = new HashMap<>();
        result.put(ApplicationConstants.GIFTS_SEARCH_RESULTS, giftsBySearchString);
        result.put(ApplicationConstants.USERS_SEARCH_RESULTS, usersByUserInput);

        return result;
    }

    @Override
    public List<SmartUser> findUsersWithOffset(int offset, SmartUser smartUser) {
        return smartUserDAO.findSmartUsersByOffset(offset, smartUser.getUuid());
    }

    @Override
    public void updateUserFile(SmartUser smartUser, File file) {
        smartUser.setFile(file);
        smartUserDAO.merge(smartUser);
    }

    @Override
    public void addRequestSmartUserFriend(SmartUser activeUser, String friendUsername) {
        SmartUserFriend userNewFriend = new SmartUserFriend();
        userNewFriend.setFriend(smartUserDAO.findSmartUserByUsername(friendUsername));
        userNewFriend.setFriendAddDate(new Date());
        userNewFriend.setFriendTypeId(ApplicationConstants.USER_FRIEND_REQUEST_TYPE);
        userNewFriend.setSmartUser(activeUser);

        activeUser.getSmartUserFriends().add(userNewFriend);

        smartUserDAO.store(activeUser);
    }

    @Override
    public void removeSmartUserFriend(SmartUser activeUser, String friendUsername) {
        SmartUser friend = smartUserDAO.findSmartUserByUsername(friendUsername);
        SmartUserFriend smartUserFriend = smartUserDAO.findSmartUserFriend(activeUser, friend);
        activeUser.getSmartUserFriends().remove(smartUserFriend);
        smartUserDAO.store(activeUser);
    }

    @Override
    public List<SmartUserFriend> findAllSmartUserFriends(SmartUser activeUser) {
        return smartUserDAO.findAllSmartUserFriends(activeUser);
    }

    @Override
    public void changeSmartUserFriendType(SmartUser activeUser, String friendUsername, int typeId) {
        SmartUser friend = smartUserDAO.findSmartUserByUsername(friendUsername);
        Set<SmartUserFriend> smartUserFriends = activeUser.getSmartUserFriends();

        for (SmartUserFriend currentSmartUserFriend : smartUserFriends) {
            if (friend.getUuid().equals(currentSmartUserFriend.getFriend().getUuid())) {
                currentSmartUserFriend.setFriendTypeId(typeId);
                break;
            }
        }

        smartUserDAO.merge(activeUser);
    }

    private List<SmartUser> removeDuplicates(List<SmartUser> l) {
        Set<SmartUser> s = new TreeSet<>(new Comparator<SmartUser>() {

            @Override
            public int compare(SmartUser o1, SmartUser o2) {
                if (o1.getUuid().equals(o2.getUuid())) {
                    return 0;
                }
                return 1;
            }
        });
        s.addAll(l);
        return new ArrayList<>(s);
    }
}
