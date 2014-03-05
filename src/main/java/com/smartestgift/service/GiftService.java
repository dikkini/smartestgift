package com.smartestgift.service;

import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserGift;

import java.util.Set;

/**
 * Created by dikkini on 28.02.14.
 * Email: dikkini@gmail.com
 */
public interface GiftService {

    /**
     * if the user has this gift
     * @param smartUserGifts Set of SmartUserGift
     * @param gift Gift
     * @return true - had
     */
    public boolean smartUserHasGift(Set<SmartUserGift> smartUserGifts, Gift gift);

    /**
     * Add gift to user
     * @param user SmartUser model
     * @param gift Gift model
     */
    public void addGiftToUserWishes(SmartUser user, Gift gift);

    /**
     *
     * @param user
     * @param gift
     * @return
     */
    public void deleteGiftFromUser(SmartUser user, Gift gift);
}
