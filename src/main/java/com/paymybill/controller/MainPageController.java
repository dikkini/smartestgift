package com.paymybill.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController {

    private Logger logger = Logger.getLogger(this.getClass());

    @GetMapping
    @RequestMapping(value = "/")
    public ModelAndView indexPage() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
}
