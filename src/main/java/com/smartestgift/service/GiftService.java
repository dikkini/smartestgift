package com.smartestgift.service;

import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.SmartUser;

/**
 * Created by dikkini on 28.02.14.
 * Email: dikkini@gmail.com
 */
public interface GiftService {

    public void addGiftToUserWishes(SmartUser user, Gift gift);
}
