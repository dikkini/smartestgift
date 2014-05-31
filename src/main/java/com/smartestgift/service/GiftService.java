package com.smartestgift.service;

import com.smartestgift.controller.model.GiftPage;
import com.smartestgift.dao.model.*;

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
     * if the user has this gift
     * @param smartUserGifts Set of SmartUserGift
     * @param giftShop Gift in specific shop
     * @return true - had
     */
    public boolean smartUserHasGiftShop(Set<SmartUserGift> smartUserGifts, GiftShop giftShop);

    /**
     * Add gift to user
     * @param user SmartUser model
     * @param giftShop Gift model
     */
    public void addGiftShopToUserWishes(SmartUser user, GiftShop giftShop);

    /**
     *
     * @param user
     * @param giftShop
     * @return
     */
    public void deleteGiftFromUser(SmartUser user, GiftShop giftShop);

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
}
