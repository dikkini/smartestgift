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
    public List<Gift> findGiftsLimitSize(int offset, int count, GiftCategory category);
}
