package com.smartestgift.controller;

import com.google.gson.Gson;
import com.smartestgift.controller.model.AjaxResponse;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
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
    public @ResponseBody String searchPeople(@ActiveUser SmartUserDetails smartUserDetails,
                                             @RequestParam(required = true, value = "searchPeopleStr")
                                             String searchPeopleStr) {

        List<SmartUser> usersByUserInput = smartUserService.findUsersByUserInput(searchPeopleStr,
                smartUserDetails.getSmartUser());

        return gson.toJson(usersByUserInput);
    }

    @RequestMapping(value = "/globalSearch", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody String globlSearch(@ActiveUser SmartUserDetails smartUserDetails,
                                            @RequestParam(required = true, value = "searchString") String searchString) {
        Map<String, List> usersAndGiftsByUserInput = smartUserService.findUsersAndGiftsByUserInput(searchString,
                smartUserDetails.getSmartUser());

        return gson.toJson(usersAndGiftsByUserInput);
    }
}