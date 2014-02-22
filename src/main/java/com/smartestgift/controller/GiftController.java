package com.smartestgift.controller;

import com.smartestgift.dao.GiftCategoryDAO;
import com.smartestgift.dao.model.GiftCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by dikkini on 14.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class GiftController {

    @Autowired
    GiftCategoryDAO giftCategoryDAO;

    @RequestMapping(value = "/gifts/mygifts", method = RequestMethod.GET)
    public ModelAndView myGifts() {
        ModelAndView mav = new ModelAndView("gifts/mygifts");
        return mav;
    }

    @RequestMapping(value = "/gifts/find", method = RequestMethod.GET)
    public ModelAndView findGift() {
        ModelAndView mav = new ModelAndView("gifts/find");
        return mav;
    }

    @RequestMapping(value = "/gifts/categories/{giftCategoryId}", method = RequestMethod.GET)
    public ModelAndView gifts(@PathVariable Integer giftCategoryId) {
        List<GiftCategory> allGiftCategories = giftCategoryDAO.findAll();
        ModelAndView mav = new ModelAndView("gifts/categories");
        mav.addObject("allGiftCategories", allGiftCategories);

        GiftCategory giftCategory = null;
        if (giftCategoryId != 0) {
            giftCategory = giftCategoryDAO.find(giftCategoryId);
            mav.addObject("giftCategory", giftCategory);
        }
        mav.addObject("giftCategory", giftCategory);
        return mav;
    }

    @RequestMapping(value = "/gifts/randomGift", method = RequestMethod.GET)
    public String getRandomGift() {
        return "redirect:/gifts/235334";
    }
}
