package com.smartestgift.controller.model;

import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.GiftCategory;

import java.util.List;

/**
 * Created by dikkini on 16.05.14.
 * Email: dikkini@gmail.com
 */
public class GiftPage extends Page {

    public GiftPage(List<Gift> results, int pageNum, int pageSize, boolean isNextPage, boolean isPreviousPage,
                    GiftCategory giftCategory, Long countAll) {
        this.results = results;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.isNextPage = isNextPage;
        this.isPreviousPage = isPreviousPage;
        this.countAll = countAll;
    }
}
