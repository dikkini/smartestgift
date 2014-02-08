package com.smartestgift.controller;

import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    SmartUserDAO smartUserDAO;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView signin(@RequestParam(value = "id", required = true) String userUuid) {
        ModelAndView mav = new ModelAndView("profile");
        SmartUserDetails smartUserDetails = smartUserDAO.find(userUuid);
        SmartUser smartUser = smartUserDetails.getSmartUser();
        mav.addObject("smartUser", smartUser);
        return mav;
    }
}