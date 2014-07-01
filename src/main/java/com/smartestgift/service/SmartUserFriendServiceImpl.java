package com.smartestgift.service;

import com.smartestgift.dao.*;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.smartestgift.utils.ApplicationConstants.USER_FRIEND_NEW_REQUEST_TYPE;

/**
 * Created by dikkini on 01/07/14.
 */
@Service
public class SmartUserFriendServiceImpl implements SmartUserFriendService {

    @Autowired
    private SmartUserDAO smartUserDAO;

    @Override
    public void addRequestSmartUserFriend(SmartUser activeUser, String friendUsername) {
        SmartUserFriend userNewFriend = new SmartUserFriend();
        userNewFriend.setFriendUser(smartUserDAO.findSmartUserByUsername(friendUsername));
        userNewFriend.setFriendAddDate(new Date());
        userNewFriend.setFriendTypeId(USER_FRIEND_NEW_REQUEST_TYPE);
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
}
