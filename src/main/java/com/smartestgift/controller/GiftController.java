package com.smartestgift.controller;

import com.smartestgift.controller.model.AjaxResponse;
import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.dao.model.SmartUserGift;
import com.smartestgift.service.GiftService;
import com.smartestgift.utils.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

import static com.smartestgift.utils.ResponseMessages.*;
import static com.smartestgift.utils.Utils.isUUID;

/**
 * Created by dikkini on 14.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
@RequestMapping("/gifts")
public class GiftController {

    @Autowired
    GiftService giftService;

    @RequestMapping(value = "/mygifts", method = RequestMethod.GET)
    public ModelAndView myGifts(@ActiveUser SmartUserDetails smartUserDetails) {
        Set<SmartUserGift> gifts = smartUserDetails.getSmartUser().getSmartUserGifts();
        return new ModelAndView("gifts/mygifts", "gifts", gifts);
    }

    // TODO add event to news feed
    // TODO sending email to a friends of users if option checked true to send
    @RequestMapping(value = "/wantGift", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse wantGift(@ActiveUser SmartUserDetails smartUserDetails, @RequestParam(required = true, value = "giftuuid") String giftUuid) {
        AjaxResponse result = new AjaxResponse();

/*        if (!isUUID(giftUuid)) {
            result.setSuccess(false);
            result.addError(ResponseMessages.USER_ADD_GIFT_ERROR);
            return result;
        }*/

        Gift gift = giftService.findGiftByUuid(giftUuid);

        if (!giftService.smartUserHasGift(smartUserDetails.getSmartUser().getSmartUserGifts(), gift)) {
            giftService.addGiftToUserWishes(smartUserDetails.getSmartUser(), gift);
            result.setSuccess(true);
            result.addSuccessMessage(USER_ADD_GIFT_SUCCESS);
        } else {
            result.setSuccess(false);
            result.addError(USER_HAD_GIFT_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/unWantGift", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse unWantGift(@ActiveUser SmartUserDetails smartUserDetails, @RequestParam(required = true, value = "giftuuid") String giftUuid) {
        AjaxResponse result = new AjaxResponse();

//        if (!isUUID(giftUuid)) {
//            result.setSuccess(false);
//            result.addError(ResponseMessages.INTERNAL_ERROR);
//            return result;
//        }

        Gift gift = giftService.findGiftByUuid(giftUuid);

        if (giftService.smartUserHasGift(smartUserDetails.getSmartUser().getSmartUserGifts(), gift)) {
            giftService.deleteGiftFromUser(smartUserDetails.getSmartUser(), gift);
            result.setSuccess(true);
            result.addSuccessMessage(DELETE_GIFT_FROM_USER_SUCCESS);
        } else {
            result.setSuccess(false);
            result.addError(DELETE_GIFT_FROM_USER_ERROR);
        }
        return result;
    }
}
