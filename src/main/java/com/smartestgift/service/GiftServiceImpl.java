package com.smartestgift.service;

import com.smartestgift.dao.GiftDAO;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserGift;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.www.content.image.gif;

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
    public void addGiftToUserWishes(SmartUser user, Gift gift) {
        SmartUserGift userGift = new SmartUserGift();
        userGift.setSmartUser(user);
        userGift.setGift(gift);
        userGift.setMoneyCollect(0);

        user.getSmartUserGifts().add(userGift);

        Session session = sessionFactory.getCurrentSession();
        session.update(user);
        session.flush();
    }
}
