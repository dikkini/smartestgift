package com.smartestgift.service;

import com.smartestgift.controller.model.GiftPage;
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
import sun.net.www.content.image.gif;

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
        for (Iterator<SmartUserGift> smartUserGiftIterator = smartUserGifts.iterator(); smartUserGiftIterator.hasNext(); ) {
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
    public GiftPage getPageOfGifts(boolean nextPage, int pageNum, int pageSize, String categoryCode) {
        int start = (pageNum - 1) * pageSize;
        /*
        прибавляем в размеру страницы единицу чтобы понять есть ли следующая страница
         */
        int end = start + pageSize + 1;

        GiftCategory giftCategory = giftCategoryDAO.findByCode(categoryCode);

        List<Gift> giftsLimitSize = giftDAO.findGiftsLimitSizeByCategory(start, end, giftCategory);

        return buildGiftPage(giftsLimitSize, nextPage, pageNum, pageSize, giftCategory);
    }

    @Override
    public GiftPage getPageOfGiftsBySearchString(boolean nextPage, int pageNum, int pageSize, String searchString) {
        int start = (pageNum - 1) * pageSize;
        /*
        прибавляем в размеру страницы единицу чтобы понять есть ли следующая страница
         */
        int end = start + pageSize + 1;

        List<Gift> giftsLimitSize = giftDAO.findGiftsLimitSizeBySearchString(start, end, searchString);

        return buildGiftPage(giftsLimitSize, nextPage, pageNum, pageSize, null);
    }


    private GiftPage buildGiftPage(List<Gift> gifts, boolean nextPage, int pageNum, int pageSize, GiftCategory giftCategory) {
        boolean isNextPage;
        boolean isPreviousPage;

        // TODO может стоить поменять механизм понимания наличия следующей страницы (?)
        if (nextPage) {
            isPreviousPage = pageNum != 1;
            isNextPage = gifts.size() > pageSize;
        } else {
            isNextPage = true;
            isPreviousPage = pageNum > 1;
        }

        // убираем последствия понимания следующей страницы
        if (gifts.size() > pageSize) {
            gifts.remove(gifts.size() - 1);
        }

        return new GiftPage(gifts, pageNum, pageSize, isNextPage, isPreviousPage, giftCategory);
    }


}
