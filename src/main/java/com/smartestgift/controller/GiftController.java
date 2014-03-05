package com.smartestgift.controller;

import com.smartestgift.controller.model.AjaxResponse;
import com.smartestgift.dao.GiftDAO;
import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.dao.model.SmartUserGift;
import com.smartestgift.service.GiftService;
import com.smartestgift.utils.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

/**
 * Created by dikkini on 14.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class GiftController {

    @Autowired
    GiftService giftService;

    @Autowired
    GiftDAO giftDAO;

    @RequestMapping(value = "/gifts/mygifts", method = RequestMethod.GET)
    public ModelAndView myGifts() {
        SmartUserDetails smartUserDetails = (SmartUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<SmartUserGift> gifts = smartUserDetails.getSmartUser().getSmartUserGifts();
        return new ModelAndView("gifts/mygifts", "gifts", gifts);
    }

    @RequestMapping(value = "/gifts/removeGift", method = RequestMethod.POST)
    public AjaxResponse deleteGift(@RequestParam(required = true, value = "giftuuid") String giftUuid) {
        AjaxResponse result = new AjaxResponse();
        SmartUserDetails smartUserDetails = (SmartUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gift gift = giftDAO.find(giftUuid);
        giftService.deleteGiftFromUser(smartUserDetails.getSmartUser(), gift);
        result.addSuccessMessage(ResponseMessages.DELETE_GIFT_FROM_USER_SUCCESS);
        result.setSuccess(true);
        return result;
    }
}
