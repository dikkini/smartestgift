package com.smartestgift.dao;

import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.GiftCategory;

import java.util.List;

/**
 * Created by dikkini on 22.02.14.
 * Email: dikkini@gmail.com
 */
public interface GiftDAO extends Repository<Gift, String> {

    /**
     *
     * @param offset
     * @param count
     * @param category
     * @return
     */
    public List<Gift> findGiftsLimitSizeByCategory(int offset, int count, GiftCategory category);

    /**
     *
     * @param offset
     * @param count
     * @param searchString
     * @return
     */
    public List<Gift> findGiftsLimitSizeBySearchString(int offset, int count, String searchString);

    /**
     *
     * @param searchString
     * @return
     */
    public List<Gift> findGiftsBySearchString(String searchString);

    /**
     *
     * @param categoryCode
     * @return
     */
    public Long findCountAllGiftsByCategoryCode(String categoryCode);

    /**
     *
     * @param searchString
     * @return
     */
    public Long findCountAllGiftsBySearchString(String searchString);
}
