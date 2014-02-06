package com.smartestgift.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dikkini on 29.01.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class IndexController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView signin() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
}