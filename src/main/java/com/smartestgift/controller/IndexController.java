package com.smartestgift.controller;

import com.google.gson.Gson;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.service.SmartUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by dikkini on 29.01.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class IndexController {

    @Autowired
    SmartUserService smartUserService;

    @Autowired
    Gson gson;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView signin() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    // TODO продумать api, может сейчас сделать это
    @RequestMapping(value = "/searchPeople", headers="Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody String searchPeople(Authentication authentication,
                                             @RequestParam(required = true, value = "searchPeopleStr")
                                             String searchPeopleStr) {

        List<SmartUser> usersByUserInput = smartUserService.findUsersByUserInput(searchPeopleStr,
                smartUserService.findUserByUsername(authentication.getName()));

        return gson.toJson(usersByUserInput);
    }

    @RequestMapping(value = "/globalSearch", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody String globlSearch(Authentication authentication,
                                            @RequestParam(required = true, value = "searchString") String searchString) {
        Map<String, List> usersAndGiftsByUserInput = smartUserService.findUsersAndGiftsByUserInput(searchString,
                smartUserService.findUserByUsername(authentication.getName()));

        return gson.toJson(usersAndGiftsByUserInput);
    }
}