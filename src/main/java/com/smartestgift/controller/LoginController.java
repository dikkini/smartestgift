package com.smartestgift.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView signin(@RequestParam(required = false, value = "error") boolean error) {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("error", error);
        return mav;
    }
}