package com.smartestgift.controller;

import com.google.gson.Gson;
import com.smartestgift.controller.model.AjaxResponse;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserFriend;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @RequestMapping(value = "/myfriends", method = RequestMethod.GET)
    public ModelAndView myFriends(Authentication authentication) {
        List<SmartUserFriend> allSmartUserFriends = smartUserService.findAllSmartUserFriends(smartUserService.findUserByUsername(authentication.getName()));
        return new ModelAndView("users/myfriends").addObject("smartUserFriends", allSmartUserFriends);
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
    public
    @ResponseBody
    String findPeople(Authentication authentication,
                      @RequestParam(value = "offset", required = true) int offset) {
        List<SmartUser> usersWithOffset = smartUserService.findUsersWithOffset(offset, smartUserService.findUserByUsername(authentication.getName()));
        return gson.toJson(usersWithOffset);
    }

    @RequestMapping(value = "/addFriendRequest.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    AjaxResponse addFriendRequest(Authentication authentication,
                                  @RequestParam(value = "friendUsername", required = true) String friendUsername) {
        AjaxResponse response = new AjaxResponse();
        smartUserService.addRequestSmartUserFriend(smartUserService.findUserByUsername(authentication.getName()), friendUsername);
        response.setSuccess(true);
        return response;
    }

    @RequestMapping(value = "/removeFriend.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    AjaxResponse removeFriend(Authentication authentication,
                              @RequestParam(value = "friendUsername", required = true) String friendUsername) {
        AjaxResponse response = new AjaxResponse();
        smartUserService.removeSmartUserFriend(smartUserService.findUserByUsername(authentication.getName()), friendUsername);
        response.setSuccess(true);
        return response;
    }

    @RequestMapping(value = "/acceptFriendRequest.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    AjaxResponse acceptFriendRequest(Authentication authentication,
                                     @RequestParam(value = "friendUsername", required = true) String friendUsername) {
        AjaxResponse response = new AjaxResponse();
        smartUserService.changeSmartUserFriendType(smartUserService.findUserByUsername(authentication.getName()), friendUsername, ApplicationConstants.USER_FRIEND_FRIEND_TYPE);
        response.setSuccess(true);
        return response;
    }

    @RequestMapping(value = "/declineFriendRequest.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    AjaxResponse declineFriendRequest(Authentication authentication,
                                      @RequestParam(value = "friendUsername", required = true) String friendUsername) {
        AjaxResponse response = new AjaxResponse();
        smartUserService.removeSmartUserFriend(smartUserService.findUserByUsername(authentication.getName()), friendUsername);
        response.setSuccess(true);
        return response;
    }

    @RequestMapping(value = "/blockFriend.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public
    @ResponseBody
    AjaxResponse blockFriend(Authentication authentication,
                             @RequestParam(value = "friendUuid", required = true) String friendUuid) {
        AjaxResponse response = new AjaxResponse();
        smartUserService.changeSmartUserFriendType(smartUserService.findUserByUsername(authentication.getName()), friendUuid, ApplicationConstants.USER_FRIEND_BLOCK_TYPE);
        response.setSuccess(true);
        return response;
    }
}
