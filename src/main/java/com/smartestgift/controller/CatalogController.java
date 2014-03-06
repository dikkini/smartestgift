package com.smartestgift.controller;

import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.GiftCategory;
import com.smartestgift.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by dikkini on 04.03.14.
 * Email: dikkini@gmail.com
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    GiftService giftService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView giftCategories() {
        List<GiftCategory> allGiftCategories = giftService.findAllGiftCategories();
        ModelAndView mav = new ModelAndView("catalog/catalog");
        mav.addObject("allGiftCategories", allGiftCategories);
        return mav;
    }

    @RequestMapping(value = "/{giftCategoryCode}", method = RequestMethod.GET)
    public ModelAndView giftCategory(@PathVariable String giftCategoryCode) {
        List<GiftCategory> allGiftCategories = giftService.findAllGiftCategories();
        ModelAndView mav = new ModelAndView("catalog/catalog");
        mav.addObject("allGiftCategories", allGiftCategories);
        GiftCategory giftCategory = giftService.findGiftCategoryByCode(giftCategoryCode);
        mav.addObject("giftCategory", giftCategory);
        return mav;
    }

    @RequestMapping(value = "/{giftCategoryCode}/{giftUuid}", method = RequestMethod.GET)
    public ModelAndView giftPage(@PathVariable String giftCategoryCode, @PathVariable String giftUuid) {
        // TODO check what to do with giftcategorycode
        Gift gift = giftService.findGiftByUuid(giftUuid);
        return new ModelAndView("catalog/gift", "gift", gift);
    }

    @RequestMapping(value = "/randomGift", method = RequestMethod.GET)
    public String getRandomGift() {
        // TODO do :-)
        return "redirect:/gifts/235334";
    }
}
