package com.smartestgift.controller;

import com.smartestgift.controller.model.Response;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserGiftURL;
import com.smartestgift.exception.InternalErrorException;
import com.smartestgift.service.SmartUserGiftService;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    private SmartUserService smartUserService;

    @Autowired
    private SmartUserGiftService smartUserGiftService;

    @RequestMapping(value = "/{shorturl}")
    public String userGiftShortUrl(@PathVariable String shorturl) {
        SmartUserGiftURL smartUserGiftURLByShortURL = smartUserGiftService.findSmartUserGiftURLByShortURL(shorturl);
        if (smartUserGiftURLByShortURL == null) {
            throw new InternalErrorException("TODO wrong shorturl. need some redirect",
                    ApplicationConstants.INTERNAL_EXCEPTION_CODE);
        }
        return "redirect:" + smartUserGiftURLByShortURL.getUrl();
    }

    @RequestMapping(value = "/{username}/{giftName}", method = RequestMethod.GET)
    public ModelAndView userGiftShopDonate(@PathVariable String username, @PathVariable String giftName) {
        System.out.println(username);
        System.out.println(giftName);
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    // TODO продумать api, может сейчас сделать это
    @RequestMapping(value = "/searchPeople", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody Response searchPeople(Authentication authentication,
                                               @RequestParam(required = true, value = "searchPeopleStr")
                                               String searchPeopleStr) {

        SmartUser userByUsername = smartUserService.findByUsername(authentication.getName());
        List<SmartUser> usersByUserInput = smartUserService.findByUserInput(searchPeopleStr, userByUsername);
        return Response.createResponse(usersByUserInput);
    }

    @ExceptionHandler(value = Exception.class)
    @RequestMapping(value = "/globalSearch", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody Response globlSearch(Authentication authentication,
                                              @RequestParam(required = true, value = "searchString")
                                              String searchString) {
        SmartUser userByUsername = smartUserService.findByUsername(authentication.getName());
        Map<String, List> usersAndGiftsByUserInput = smartUserService.findUsersAndGiftsByUserInput(searchString,
                userByUsername);
        return Response.createResponse(usersAndGiftsByUserInput);
    }
}