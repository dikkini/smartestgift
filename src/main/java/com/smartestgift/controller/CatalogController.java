package com.smartestgift.controller;

import com.smartestgift.controller.model.AjaxResponse;
import com.smartestgift.dao.GiftCategoryDAO;
import com.smartestgift.dao.GiftDAO;
import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.GiftCategory;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.service.GiftService;
import com.smartestgift.utils.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.net.www.content.image.gif;

import java.util.List;
import java.util.UUID;

import static com.smartestgift.utils.ResponseMessages.*;
import static com.smartestgift.utils.Utils.isUUID;

/**
 * Created by dikkini on 04.03.14.
 * Email: dikkini@gmail.com
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    GiftCategoryDAO giftCategoryDAO;

    @Autowired
    GiftDAO giftDAO;

    @Autowired
    GiftService giftService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView giftCategories() {
        List<GiftCategory> allGiftCategories = giftCategoryDAO.findAll();
        ModelAndView mav = new ModelAndView("catalog/catalog");
        mav.addObject("allGiftCategories", allGiftCategories);
        return mav;
    }

    @RequestMapping(value = "/{giftCategoryCode}", method = RequestMethod.GET)
    public ModelAndView giftCategory(@PathVariable String giftCategoryCode) {
        List<GiftCategory> allGiftCategories = giftCategoryDAO.findAll();
        ModelAndView mav = new ModelAndView("catalog/catalog");
        mav.addObject("allGiftCategories", allGiftCategories);
        GiftCategory giftCategory = giftCategoryDAO.findByCode(giftCategoryCode);
        mav.addObject("giftCategory", giftCategory);
        return mav;
    }

    @RequestMapping(value = "/{giftCategoryCode}/{giftId}", method = RequestMethod.GET)
    public ModelAndView giftPage(@PathVariable String giftCategoryCode, @PathVariable String giftId) {
        // TODO check what to do with giftcategorycode
        Gift gift = giftDAO.find(giftId);
        return new ModelAndView("catalog/gift", "gift", gift);
    }

    @RequestMapping(value = "/randomGift", method = RequestMethod.GET)
    public String getRandomGift() {
        // TODO do :-)
        return "redirect:/gifts/235334";
    }

    // TODO add event to news feed
    // TODO sending email to a friends of users if option checked true to send
    @RequestMapping(value = "/wantGift", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse wantGift(@RequestParam(required = true, value = "giftuuid") String giftUuid) {
        AjaxResponse result = new AjaxResponse();

/*        if (!isUUID(giftUuid)) {
            result.setSuccess(false);
            result.addError(ResponseMessages.USER_ADD_GIFT_ERROR);
            return result;
        }*/

        SmartUserDetails smartUserDetails = (SmartUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gift gift = giftDAO.find(giftUuid);

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
}
