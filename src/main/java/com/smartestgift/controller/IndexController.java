package com.smartestgift.controller;

import com.google.gson.Gson;
import com.smartestgift.controller.model.Response;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.service.SmartUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

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
    @RequestMapping(value = "/searchPeople", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody Response searchPeople(Authentication authentication,
                                               @RequestParam(required = true, value = "searchPeopleStr")
                                               String searchPeopleStr) {

        SmartUser userByUsername = smartUserService.findUserByUsername(authentication.getName());
        List<SmartUser> usersByUserInput = smartUserService.findUsersByUserInput(searchPeopleStr, userByUsername);
        return Response.createResponse(usersByUserInput);
    }

    @ExceptionHandler(value = Exception.class)
    @RequestMapping(value = "/globalSearch", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody Response globlSearch(Authentication authentication,
                                              @RequestParam(required = true, value = "searchString")
                                              String searchString) {
        SmartUser userByUsername = smartUserService.findUserByUsername(authentication.getName());
        Map<String, List> usersAndGiftsByUserInput = smartUserService.findUsersAndGiftsByUserInput(searchString,
                userByUsername);
        return Response.createResponse(usersAndGiftsByUserInput);
    }
}