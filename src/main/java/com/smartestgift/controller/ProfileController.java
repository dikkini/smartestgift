package com.smartestgift.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dikkini on 06.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class ProfileController {
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView signin() {
        ModelAndView mav = new ModelAndView("profile");
        return mav;
    }
}