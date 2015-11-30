package com.smartestgift.service;

import com.smartestgift.dao.model.SmartUserGiftURL;

/**
 * Created by dikkini on 01/07/14.
 */
public interface SmartUserGiftService {

    /**
     *
     * @param shortUrl
     * @return
     */
    public SmartUserGiftURL findSmartUserGiftURLByShortURL(String shortUrl);
}
