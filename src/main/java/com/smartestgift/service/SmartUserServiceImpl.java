package com.smartestgift.service;

import com.smartestgift.dao.*;
import com.smartestgift.dao.model.*;
import com.smartestgift.exception.InternalErrorException;
import com.smartestgift.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.smartestgift.utils.ApplicationConstants.*;

/**
 * Created by dikkini on 18.02.14.
 * Email: dikkini@gmail.com
 */
@Service
public class SmartUserServiceImpl implements SmartUserService {

    @Autowired
    private SmartUserDAO smartUserDAO;

    @Autowired
    private GiftDAO giftDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private FileDAO fileDAO;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public SmartUser findUserByUsername(String username) {
        return smartUserDAO.findSmartUserByUsername(username);
    }

    @Override
    public SmartUser createSmartUser(String username, String password, String email, String lastName, String firstName,
                                     Date registrationDate, int authProvider, boolean enabled) {
        // TODO решить вопрос с файлом
        SmartUser smartUser = new SmartUser(username, password, email, lastName, firstName, registrationDate,
                authProvider, enabled);
        smartUser.setFile(fileDAO.find(FILE_USER_NO_PHOTO_ID));
        return this.createSmartUser(smartUser);
    }

    @Override
    public SmartUser createSmartUser(SmartUser smartUser) {
        smartUserDAO.store(smartUser);
        return smartUser;
    }

    @Override
    public SmartUser findExistSocialUser(String socialId, Integer providerId) {
        return smartUserDAO.findUserBySocialIdAndAuthProvider(socialId, providerId);
    }

    @Override
    public boolean isUsernameBusy(String username) {
        SmartUser userDetailsByUsername = smartUserDAO.findSmartUserByUsername(username);
        return userDetailsByUsername != null;
    }

    @Override
    public boolean isEmailBusy(String email) {
        SmartUser smartUserDetailsByEmail = smartUserDAO.findSmartUserByEmail(email);
        return smartUserDetailsByEmail != null;
    }

    @Override
    public void createUserAuthorityForUser(String username) {
        Role role = roleDAO.find(USER_ROLE_ID);
        SmartUser smartUser = smartUserDAO.findSmartUserByUsername(username);
        UserRole userRole = new UserRole(role, smartUser);
        smartUser.getUserRoles().add(userRole);
        smartUserDAO.store(smartUser);
    }

    @Override
    public void authenticateUser(String userName, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        // Create a new session and add the security context.
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    @Override
    public void checkUserAddress(SmartUser SmartUser) {
        // TODO do it
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
        result.put(GIFTS_SEARCH_RESULTS, giftsBySearchString);
        result.put(USERS_SEARCH_RESULTS, usersByUserInput);

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
        userNewFriend.setFriendUser(smartUserDAO.findSmartUserByUsername(friendUsername));
        userNewFriend.setFriendAddDate(new Date());
        userNewFriend.setFriendTypeId(USER_FRIEND_REQUEST_TYPE);
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
            if (friend.getUuid().equals(currentSmartUserFriend.getFriendUser().getUuid())) {
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
