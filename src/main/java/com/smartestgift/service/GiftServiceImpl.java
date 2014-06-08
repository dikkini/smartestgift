package com.smartestgift.service;

import com.smartestgift.controller.model.GiftPage;
import com.smartestgift.dao.*;
import com.smartestgift.dao.model.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.www.content.image.gif;

import javax.transaction.Transactional;
import java.util.Date;
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
    ShopDAO shopDAO;

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    SmartUserDAO smartUserDAO;

    @Autowired
    GiftShopDAO giftShopDAO;

    @Override
    public Gift findGiftByUuid(String uuid) {
        return giftDAO.find(uuid);
    }

    @Override
    public GiftShop findGiftShopByUuid(String uuid) {
        return giftShopDAO.find(uuid);
    }

    @Override
    public boolean smartUserHasGiftShop(Set<SmartUserGift> smartUserGifts, GiftShop giftShop) {
        for (SmartUserGift smartuserGift : smartUserGifts) {
            if (smartuserGift.getGiftShop().getUuid().equals(giftShop.getUuid())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addGiftShopToUserWishes(SmartUser user, GiftShop giftShop, Date endDate) {
        SmartUserGift smartUserGift = new SmartUserGift();
        smartUserGift.setSmartUser(user);
        smartUserGift.setGiftShop(giftShop);
        smartUserGift.setMoneyCollect(0);
        smartUserGift.setEndDate(endDate);
        user.getSmartUserGifts().add(smartUserGift);
        smartUserDAO.merge(user);
    }

    @Override
    public void deleteGiftFromUser(SmartUser user, GiftShop giftShop) {
        Set<SmartUserGift> smartUserGifts = user.getSmartUserGifts();
        for (Iterator<SmartUserGift> smartUserGiftIterator = smartUserGifts.iterator(); smartUserGiftIterator.hasNext(); ) {
            SmartUserGift currentSmartUserGift = smartUserGiftIterator.next();
            if (giftShop.getUuid().equals(currentSmartUserGift.getGiftShop().getUuid())) {
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
    public GiftPage getPageOfGifts(Long countAll, int pageNum, int pageSize, String categoryCode) {
        if (countAll == -1) {
            countAll = giftDAO.findCountAllGiftsByCategoryCode(categoryCode);
        }

        int start = pageNum * pageSize - pageSize;
        int end = start + pageSize - 1;

        boolean isNextPage = end < countAll;
        boolean isPreviousPage = start != 0;

        GiftCategory giftCategory = giftCategoryDAO.findByCode(categoryCode);

        List<Gift> gifts = giftDAO.findGiftsLimitSizeByCategory(start, pageSize, giftCategory);

        return new GiftPage(gifts, pageNum, pageSize, isNextPage, isPreviousPage, giftCategory, countAll);
    }

    @Override
    public GiftPage getPageOfGiftsBySearchString(Long countAll, int pageNum, int pageSize, String searchString) {
        if (countAll == -1) {
            countAll = giftDAO.findCountAllGiftsBySearchString(searchString);
        }

        int start = pageNum * pageSize - pageSize;
        int end = start + pageSize - 1;

        boolean isNextPage = end < countAll;
        boolean isPreviousPage = start != 0;

        List<Gift> gifts = giftDAO.findGiftsLimitSizeBySearchString(start, pageSize, searchString);

        return new GiftPage(gifts, pageNum, pageSize, isNextPage, isPreviousPage, null, countAll);
    }

    @Override
    public List<GiftShop> findGiftShops(String giftUuid) {
        Gift gift = giftDAO.find(giftUuid);
        return giftShopDAO.findGiftShopsByGiftUuid(gift);
    }

    @Override
    public GiftShop findGiftShopByGiftAndShop(String giftUuid, String shopUuid) {
        Gift gift = giftDAO.find(giftUuid);
        Shop shop = shopDAO.find(shopUuid);

        return giftShopDAO.findGiftShopByGiftAndShop(gift, shop);
    }


}
