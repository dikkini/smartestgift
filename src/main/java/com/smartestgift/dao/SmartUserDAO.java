package com.smartestgift.dao;

import com.smartestgift.dao.model.*;

import java.util.List;

/**
 * Created by dikkini on 06.10.13.
 * Email: dikkini@gmail.com
 */
public interface SmartUserDAO extends Repository<SmartUser, String> {

    /**
     * Поиск пользователя по username
     * @param username username
     * @return модель пользователя с деталями
     */
    public SmartUser findByUsername(String username);

    /**
     *
     * @param socialId
     * @return
     */
    public SmartUser findBySocialIdAndAuthProvider(String socialId, Integer facebookAuthProvider);

    /**
     * Поиск пользователя по email
     * @param email email
     * @return
     */
    public SmartUser findByEmail(String email);

    /**
     *
     * @param username
     * @param activeUsername
     * @return
     */
    public List<SmartUser> findLikeUsername(String username, String activeUsername);

    /**
     *
     * @param firstname
     * @param activeUsername
     * @return
     */
    public List<SmartUser> findLikeFirstName(String firstname, String activeUsername);

    /**
     *
     * @param lastName
     * @param activeUsername
     * @return
     */
    public List<SmartUser> findLikeLastName(String lastName, String activeUsername);

    /**
     *
     * @param middleName
     * @param activeUsername
     * @return
     */
    public List<SmartUser> findLikeMiddleName(String middleName, String activeUsername);

    /**
     *
     * @param offset
     * @param activeUserUuid
     * @return
     */
    public List<SmartUser> findByOffset(int offset, String activeUserUuid);

    /**
     *
     * @param smartUserFriend
     */
    public void removeSmartUserFriend(SmartUserFriend smartUserFriend);

    /**
     *
     * @param smartUserGift
     */
    public void removeSmartUserGift(SmartUserGift smartUserGift);

    /**
     *
     * @param activeUser
     * @return
     */
    public List<SmartUserFriend> findAllSmartUserFriends(SmartUser activeUser);

    /**
     *
     * @param smartUser
     * @param friend
     * @return
     */
    public SmartUserFriend findSmartUserFriend(SmartUser smartUser, SmartUser friend);

    /**
     *
     * @param user
     * @param giftShop
     * @return
     */
    public SmartUserGift findSmartUserGift(SmartUser user, GiftShop giftShop);

    /**
     *
     * @param smartUserGiftURL
     * @return
     */
    public SmartUserGift findSmartUserGift(SmartUserGiftURL smartUserGiftURL);
}
