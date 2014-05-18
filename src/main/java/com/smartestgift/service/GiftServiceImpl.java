package com.smartestgift.service;

import com.smartestgift.controller.model.Page;
import com.smartestgift.dao.GiftCategoryDAO;
import com.smartestgift.dao.GiftDAO;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.GiftCategory;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserGift;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by dikkini on 28.02.14.
 * Email: dikkini@gmail.com
 */
@Service
@Transactional
public class GiftServiceImpl implements GiftService {

    @Autowired
    GiftCategoryDAO giftCategoryDAO;

    @Autowired
    GiftDAO giftDAO;

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    SmartUserDAO smartUserDAO;

    @Override
    public Gift findGiftByUuid(String uuid) {
        return giftDAO.find(uuid);
    }

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
        smartUserDAO.merge(user);
    }

    @Override
    public void deleteGiftFromUser(SmartUser user, Gift gift) {
        Set<SmartUserGift> smartUserGifts = user.getSmartUserGifts();
        for(Iterator<SmartUserGift> smartUserGiftIterator = smartUserGifts.iterator(); smartUserGiftIterator.hasNext();){
            SmartUserGift currentSmartUserGift = smartUserGiftIterator.next();
            if (gift.equals(currentSmartUserGift.getGift())) {
                smartUserGiftIterator.remove();
                break;
            }
        }
        smartUserDAO.merge(user);
    }

    @Override
    public List<GiftCategory> findAllGiftCategories() {
        return giftCategoryDAO.findAll();
    }

    @Override
    public GiftCategory findGiftCategoryByCode(String code) {
        return giftCategoryDAO.findByCode(code);
    }

    @Override
    public Page getPageOfGifts(int pageNum, int pageSize, String categoryCode) {
        int offset = pageNum*pageSize;
        GiftCategory giftCategory = giftCategoryDAO.findByCode(categoryCode);

        List<Gift> giftsLimitSize = giftDAO.findGiftsLimitSize(offset, pageSize, giftCategory);
        Page page = new Page();
        page.setResults(giftsLimitSize);
        page.setPageNum(offset);
        page.setPageSize(pageSize);
        return page;
    }
}
