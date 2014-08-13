package com.smartestgift.dao;

import com.smartestgift.dao.model.GiftCategory;

/**
 * Created by dikkini on 22.02.14.
 * Email: dikkini@gmail.com
 */
public interface GiftCategoryDAO extends Repository<GiftCategory, Integer> {

    /**
     * Finding category by code
     * @param code code category
     * @return GiftCategory
     */
    public GiftCategory findByCode(String code);
}
