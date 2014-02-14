package com.smartestgift.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dikkini on 14.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class GiftController {

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

    @RequestMapping(value = "/gifts/gifts", method = RequestMethod.GET)
    public ModelAndView gifts() {
        ModelAndView mav = new ModelAndView("gifts/categories");
        return mav;
    }

    @RequestMapping(value = "/gifts/randomGift", method = RequestMethod.GET)
    public String getRandomGift() {
        return "redirect:/gifts/235334";
    }
}
