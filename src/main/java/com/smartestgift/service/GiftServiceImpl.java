package com.smartestgift.service;

import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserGift;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by dikkini on 28.02.14.
 * Email: dikkini@gmail.com
 */
@Service
public class GiftServiceImpl implements GiftService {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    SmartUserDAO smartUserDAO;

    @Override
    public boolean smartUserHasGift(Set<SmartUserGift> smartUserGifts, Gift gift) {
        for (SmartUserGift smartuserGift : smartUserGifts) {
            if (smartuserGift.getGift().equals(gift)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addGiftToUserWishes(SmartUser user, Gift gift) {
        SmartUserGift smartUserGift = new SmartUserGift();
        smartUserGift.setSmartUser(user);
        smartUserGift.setGift(gift);
        smartUserGift.setMoneyCollect(0);
        user.getSmartUserGifts().add(smartUserGift);
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
        session.flush();
    }
}
