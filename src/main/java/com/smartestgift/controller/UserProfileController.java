package com.smartestgift.controller;

import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.SmartUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dikkini on 13.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
@RequestMapping(value = "/users")
public class UserProfileController {

    @Autowired
    SmartUserDAO smartUserDAO;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView allUsers() {
        return new ModelAndView("users/all");
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ModelAndView user(@PathVariable String username) {
        ModelAndView mav;
        SmartUser smartUser = smartUserDAO.findSmartUserByUsername(username);
        if (smartUser == null) {
            return new ModelAndView("errors/404");
        }

        mav = new ModelAndView("users/user");
        mav.addObject("alienSmartUser", smartUser);

        return mav;
    }
}
