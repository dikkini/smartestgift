package com.smartestgift.dao;

import com.smartestgift.dao.model.SmartUserGiftURL;

/**
 * Created by dikkini on 30/06/14.
 */
public interface SmartUserGiftURLDAO extends Repository<SmartUserGiftURL, String> {

    /**
     *
     * @param id
     * @return
     */
    public SmartUserGiftURL findSmartUserGiftURLById(Integer id);

    /**
     *
     * @param shortUrl
     * @return
     */
    public SmartUserGiftURL findSmartUserGiftURLByShortUrl(String shortUrl);
}
