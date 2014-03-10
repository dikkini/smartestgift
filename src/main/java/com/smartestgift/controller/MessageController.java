package com.smartestgift.controller;

import com.smartestgift.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView messages() {
        return new ModelAndView("users/messages");
    }
}
