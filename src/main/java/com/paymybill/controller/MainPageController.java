package com.paymybill.controller;


import com.paymybill.dao.UserDAO;
import com.paymybill.dao.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController {

    private Logger logger = Logger.getLogger(this.getClass());

    public MainPageController() {
    }

    @GetMapping
    @RequestMapping(value = "/")
    public ModelAndView indexPage() {
        logger.trace("index page");
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
}
