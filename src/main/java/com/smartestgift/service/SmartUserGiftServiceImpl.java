package com.smartestgift.service;

import com.smartestgift.dao.SmartUserGiftURLDAO;
import com.smartestgift.dao.model.SmartUserGiftURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dikkini on 01/07/14.
 */
@Service
public class SmartUserGiftServiceImpl implements SmartUserGiftService {

    @Autowired
    private SmartUserGiftURLDAO smartUserGiftURLDAO;

    @Override
    public SmartUserGiftURL findSmartUserGiftURLByShortURL(String shortUrl) {
        return smartUserGiftURLDAO.findSmartUserGiftURLByShortUrl(shortUrl);
    }
}
