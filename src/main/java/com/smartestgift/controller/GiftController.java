package com.smartestgift.controller;

import com.smartestgift.controller.model.AjaxResponse;
import com.smartestgift.dao.GiftCategoryDAO;
import com.smartestgift.dao.GiftDAO;
import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.GiftCategory;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.dao.model.SmartUserGift;
import com.smartestgift.enums.messages.SuccessesEnum;
import com.smartestgift.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * Created by dikkini on 14.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class GiftController {

    @Autowired
    GiftCategoryDAO giftCategoryDAO;

    @Autowired
    GiftDAO giftDAO;

    @Autowired
    GiftService giftService;

    @RequestMapping(value = "/gifts/mygifts", method = RequestMethod.GET)
    public ModelAndView myGifts() {
        SmartUserDetails smartUserDetails = (SmartUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<SmartUserGift> gifts = smartUserDetails.getSmartUser().getSmartUserGifts();
        return new ModelAndView("gifts/mygifts", "gifts", gifts);
    }

    @RequestMapping(value = "/gifts/gift/{giftId}", method = RequestMethod.GET)
    public ModelAndView giftPage(@PathVariable String giftId) {
        Gift gift = giftDAO.find(giftId);
        return new ModelAndView("gifts/gift", "gift", gift);
    }

    @RequestMapping(value = "/gifts/categories/{giftCategoryId}", method = RequestMethod.GET)
    public ModelAndView giftCategory(@PathVariable Integer giftCategoryId) {
        List<GiftCategory> allGiftCategories = giftCategoryDAO.findAll();
        ModelAndView mav = new ModelAndView("gifts/categories");
        mav.addObject("allGiftCategories", allGiftCategories);
        GiftCategory giftCategory = giftCategoryDAO.find(giftCategoryId);
        mav.addObject("giftCategory", giftCategory);
        return mav;
    }

    @RequestMapping(value = "/gifts/categories", method = RequestMethod.GET)
    public ModelAndView giftCategories() {
        List<GiftCategory> allGiftCategories = giftCategoryDAO.findAll();
        ModelAndView mav = new ModelAndView("gifts/categories");
        mav.addObject("allGiftCategories", allGiftCategories);
        return mav;
    }

    @RequestMapping(value = "/gifts/randomGift", method = RequestMethod.GET)
    public String getRandomGift() {
        return "redirect:/gifts/235334";
    }

    @RequestMapping(value = "/gifts/wantgift", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse wantGift(@RequestParam(required = true, value = "giftuuid") String giftUuid) {
        AjaxResponse result = new AjaxResponse();
        SmartUserDetails smartUserDetails = (SmartUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gift gift = giftDAO.find(giftUuid);
        giftService.addGiftToUserWishes(smartUserDetails.getSmartUser(), gift);

        result.setSuccess(true);
        result.addSuccessMessage(SuccessesEnum.user_add_gift.getCode());
        return result;
    }
}
