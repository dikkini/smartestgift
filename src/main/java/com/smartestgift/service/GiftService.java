package com.smartestgift.service;

import com.smartestgift.controller.model.GiftPage;
import com.smartestgift.dao.model.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by dikkini on 28.02.14.
 * Email: dikkini@gmail.com
 */
public interface GiftService {

    /**
     *
     * @param uuid
     * @return
     */
    public Gift findGiftByUuid(String uuid);

    /**
     *
     * @param uuid
     * @return
     */
    public GiftShop findGiftShopByUuid(String uuid);

    /**
     * Add gift to user
     * @param user SmartUser model
     * @param giftShopUuid Gift model
     * @param endDate date when user plan to collect all money for the gift
     */
    public void addGiftShopToUserWishes(SmartUser user, String giftShopUuid, Date endDate);

    /**
     *
     * @param user
     * @param giftShopUuid
     * @return
     */
    public void deleteGiftFromUser(SmartUser user, String giftShopUuid);

    /**
     *
     * @return
     */
    public List<GiftCategory> findAllGiftCategories();

    /**
     *
     * @param code
     * @return
     */
    public GiftCategory findGiftCategoryByCode(String code);

    /**
     *
     * @param countAll
     * @param pageNum
     * @param pageSize
     * @param categoryCode
     * @return
     */
    public GiftPage getPageOfGifts(Long countAll, int pageNum, int pageSize, String categoryCode);

    /**
     *
     * @param countAll
     * @param pageNum
     * @param pageSize
     * @param searchString
     * @return
     */
    public GiftPage getPageOfGiftsBySearchString(Long countAll, int pageNum, int pageSize, String searchString);

    /**
     *
     * @param giftUuid
     * @return
     */
    public List<GiftShop> findGiftShops(String giftUuid);

    /**
     *
     * @param giftUuid
     * @param shopUuid
     * @return
     */
    public GiftShop findGiftShopByGiftAndShop(String giftUuid, String shopUuid);
}
