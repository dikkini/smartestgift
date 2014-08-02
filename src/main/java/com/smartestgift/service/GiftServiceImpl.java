package com.smartestgift.service;

import com.smartestgift.controller.model.GiftPage;
import com.smartestgift.dao.*;
import com.smartestgift.dao.model.*;
import com.smartestgift.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by dikkini on 28.02.14.
 * Email: dikkini@gmail.com
 */
@Service
@Transactional
public class GiftServiceImpl implements GiftService {

    @Autowired
    private GiftCategoryDAO giftCategoryDAO;

    @Autowired
    private GiftDAO giftDAO;

    @Autowired
    private ShopDAO shopDAO;

    @Autowired
    private SmartUserDAO smartUserDAO;

    @Autowired
    private GiftShopDAO giftShopDAO;

    @Override
    public Gift findGiftByUuid(String uuid) {
        return giftDAO.findOne(uuid);
    }

    @Override
    public GiftShop findGiftShopByUuid(String uuid) {
        return giftShopDAO.findOne(uuid);
    }

    @Override
    public boolean hasSmartUserGiftShop(SmartUser user, String giftShopUuid) {
        GiftShop giftShop = giftShopDAO.findOne(giftShopUuid);
        SmartUserGift smartUserGift = smartUserDAO.findSmartUserGift(user, giftShop);
        return smartUserGift != null;
    }

    @Override
    public void addGiftShopToUserWishes(SmartUser user, String giftShopUuid, Date endDate) {
        GiftShop giftShop = giftShopDAO.findOne(giftShopUuid);

        String url = "/" + user.getUsername() +
                "/" + giftShop.getGift().getName();
        int id = Utils.decodeUrlShotener(url);

        String shortUrl = Utils.encodeUrlShotener(id);

        SmartUserGiftURL smartUserGiftURL = new SmartUserGiftURL();
        smartUserGiftURL.setShortUrl(shortUrl);
        smartUserGiftURL.setUrl(url);
        smartUserGiftURL.setId(id);

        SmartUserGift smartUserGift = new SmartUserGift();
        smartUserGift.setSmartUser(user);
        smartUserGift.setGiftShop(giftShop);
        smartUserGift.setMoneyCollect(0);
        smartUserGift.setEndDate(endDate);
        smartUserGift.setSmartUserGiftURL(smartUserGiftURL);

        user.getSmartUserGifts().add(smartUserGift);
        smartUserDAO.create(user);
    }

    @Override
    public void deleteGiftFromUser(SmartUser user, String giftShopUuid) {
        GiftShop giftShop = giftShopDAO.findOne(giftShopUuid);
        SmartUserGift smartUserGift = smartUserDAO.findSmartUserGift(user, giftShop);
        user.getSmartUserGifts().remove(smartUserGift);
        smartUserDAO.create(user);
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
        Gift gift = giftDAO.findOne(giftUuid);
        return giftShopDAO.findGiftShopsByGiftUuid(gift);
    }

    @Override
    public GiftShop findGiftShopByGiftAndShop(String giftUuid, String shopUuid) {
        Gift gift = giftDAO.findOne(giftUuid);
        Shop shop = shopDAO.findOne(shopUuid);

        return giftShopDAO.findGiftShopByGiftAndShop(gift, shop);
    }
}
