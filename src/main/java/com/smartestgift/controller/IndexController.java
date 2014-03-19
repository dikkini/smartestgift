package com.smartestgift.controller;

import com.smartestgift.controller.model.AjaxResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;

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
}