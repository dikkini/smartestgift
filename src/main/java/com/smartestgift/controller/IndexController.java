package com.smartestgift.controller;

import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dikkini on 29.01.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class IndexController {

    @Autowired
    SmartUserService smartUserService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView signin() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @RequestMapping(value = "/isUserAuthenticated", method = RequestMethod.POST)
    public @ResponseBody boolean isUserAuthenticated() {
        try {
            SmartUserDetails authUser = (SmartUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (authUser != null) {
                return true;
            }
        } catch (Exception ignored) {}

        return false;
    }

    @RequestMapping(value = "/saveMessagesCount", method = RequestMethod.POST)
    public @ResponseBody void saveMessagesCount(@ActiveUser SmartUserDetails smartUserDetails) {
        smartUserService.saveCountMessagesForUser(smartUserDetails);
    }
}