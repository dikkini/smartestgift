package com.smartestgift.controller;

import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.SmartUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dikkini on 13.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class UserProfileController {

    @Autowired
    SmartUserDAO smartUserDAO;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView userProfile(@RequestParam(required = true, value = "uuid") String userUuid) {
        ModelAndView mav = new ModelAndView("users/user");
        SmartUser smartUser = smartUserDAO.find(userUuid);
        mav.addObject("smartUser", smartUser);
        return mav;
    }
}
