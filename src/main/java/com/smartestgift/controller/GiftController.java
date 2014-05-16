package com.smartestgift.controller;

import com.google.gson.Gson;
import com.smartestgift.controller.model.AjaxResponse;
import com.smartestgift.controller.model.Page;
import com.smartestgift.dao.model.*;
import com.smartestgift.service.GiftService;
import com.smartestgift.utils.ActiveUser;
import com.smartestgift.utils.ResponseMessages;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
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

    @Autowired
    Gson gson;

    @Autowired
    SessionFactory sessionFactory;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView giftCategories() {
        List<GiftCategory> allGiftCategories = giftService.findAllGiftCategories();
        Page page = giftService.getPageOfGifts(1, 1);
        ModelAndView mav = new ModelAndView("gifts/gifts");
        mav.addObject("allGiftCategories", allGiftCategories);
        mav.addObject("pageGift", page);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void nextPage() {
    }


    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public ModelAndView myGifts(@ActiveUser SmartUserDetails smartUserDetails) {
        Set<SmartUserGift> gifts = smartUserDetails.getSmartUser().getSmartUserGifts();
        return new ModelAndView("gifts/my", "gifts", gifts);
    }

    @RequestMapping(value = "/{giftCategoryCode}/{giftUuid}", method = RequestMethod.GET)
    public ModelAndView giftPage(@PathVariable String giftCategoryCode, @PathVariable String giftUuid) {
        // TODO check what to do with giftcategorycode
        Gift gift = giftService.findGiftByUuid(giftUuid);
        return new ModelAndView("gifts/gift", "gift", gift);
    }

    // TODO add event to news feed
    // TODO sending email to a friends of users if option checked true to send
    @RequestMapping(value = "/wantGift", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse wantGift(@ActiveUser SmartUserDetails smartUserDetails,
                                               @RequestParam(required = true, value = "giftuuid") String giftUuid) {
        AjaxResponse result = new AjaxResponse();

        if (!isUUID(giftUuid)) {
            result.setSuccess(false);
            result.addError(ResponseMessages.INTERNAL_ERROR);
            return result;
        }

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
    public @ResponseBody AjaxResponse unWantGift(@ActiveUser SmartUserDetails smartUserDetails,
                                                 @RequestParam(required = true, value = "giftuuid") String giftUuid) {
        AjaxResponse result = new AjaxResponse();

        if (!isUUID(giftUuid)) {
            result.setSuccess(false);
            result.addError(ResponseMessages.INTERNAL_ERROR);
            return result;
        }

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

    @RequestMapping(value = "/randomGift", method = RequestMethod.GET)
    public String getRandomGift() {
        // TODO do :-)
        return "redirect:/gifts/235334";
    }
}
