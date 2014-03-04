package com.smartestgift.controller;

import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.dao.model.SmartUserGift;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

/**
 * Created by dikkini on 14.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class GiftController {

    @RequestMapping(value = "/gifts/mygifts", method = RequestMethod.GET)
    public ModelAndView myGifts() {
        SmartUserDetails smartUserDetails = (SmartUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<SmartUserGift> gifts = smartUserDetails.getSmartUser().getSmartUserGifts();
        return new ModelAndView("gifts/mygifts", "gifts", gifts);
    }
}
