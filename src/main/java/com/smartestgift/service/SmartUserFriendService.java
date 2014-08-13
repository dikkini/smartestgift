package com.smartestgift.service;

import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserFriend;

import java.util.List;

/**
 * Created by dikkini on 01/07/14.
 */
public interface SmartUserFriendService {
    /**
     *
     * @param activeUser
     * @param friendUsername
     */
    public void addRequestSmartUserFriend(SmartUser activeUser, String friendUsername);

    /**
     *
     * @param activeUser
     * @param friendUsername
     */
    public void removeSmartUserFriend(SmartUser activeUser, String friendUsername);

    /**
     *
     * @param activeUser
     * @return
     */
    public List<SmartUserFriend> findAllSmartUserFriends(SmartUser activeUser);

    /**
     *
     * @param activeUser
     * @param friendUsername
     * @param typeId
     */
    public void changeSmartUserFriendType(SmartUser activeUser, String friendUsername, int typeId);
}
