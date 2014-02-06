package com.smartestgift.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dikkini on 06.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class SignupController {
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signin() {
        ModelAndView mav = new ModelAndView("signup");
        return mav;
    }
}
