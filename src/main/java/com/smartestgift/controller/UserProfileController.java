package com.smartestgift.controller;

import com.google.gson.Gson;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by dikkini on 13.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
@RequestMapping(value = "/users")
public class UserProfileController {

    @Autowired
    SmartUserDAO smartUserDAO;

    @Autowired
    SmartUserService smartUserService;

    @Autowired
    Gson gson;

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

    @RequestMapping(value = "/findPeople.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody String findPeople(@ActiveUser SmartUserDetails smartUserDetails,
                                           @RequestParam(value = "offset", required = true) int offset) {
        List<SmartUser> usersWithOffset = smartUserService.findUsersWithOffset(offset, smartUserDetails.getSmartUser());
        return gson.toJson(usersWithOffset);
    }
}
