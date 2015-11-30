package com.smartestgift.dao;

import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.GiftShop;
import com.smartestgift.dao.model.Shop;

import java.util.List;

/**
 * Created by dikkini on 28/05/14.
 */
public interface GiftShopDAO extends Repository<GiftShop, String> {

    /**
     *
     * @param gift
     * @return
     */
    public List<GiftShop> findGiftShopsByGiftUuid(Gift gift);

    /**
     *
     * @param gift
     * @param shop
     * @return
     */
    public GiftShop findGiftShopByGiftAndShop(Gift gift, Shop shop);
}
