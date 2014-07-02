package com.smartestgift.controller;

import com.google.gson.Gson;
import com.smartestgift.controller.model.Response;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserFriend;
import com.smartestgift.exception.BadUserException;
import com.smartestgift.service.SmartUserFriendService;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

/**
 * Created by dikkini on 13.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
@RequestMapping(value = "/users")
public class FriendsController {

    @Autowired
    private SmartUserService smartUserService;

    @Autowired
    private SmartUserFriendService smartUserFriendService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView allUsers() {
        return new ModelAndView("users/all");
    }

    @RequestMapping(value = "/myfriends", method = RequestMethod.GET)
    public ModelAndView myFriends(Authentication authentication) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        List<SmartUserFriend> allSmartUserFriends = smartUserFriendService.findAllSmartUserFriends(smartUser);
        return new ModelAndView("users/myfriends").addObject("smartUserFriends", allSmartUserFriends);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ModelAndView user(@PathVariable String username) {
        ModelAndView mav;
        SmartUser smartUser = smartUserService.findUserByUsername(username);
        if (smartUser == null) {
            throw new BadUserException("Bad user.", ApplicationConstants.INTERNAL_EXCEPTION_CODE);
        }
        return new ModelAndView("users/user").addObject("alienSmartUser", smartUser);
    }

    @RequestMapping(value = "/findPeople.do", headers = "Accept=application/json", method = RequestMethod.GET)
    public @ResponseBody Response findPeople(Authentication authentication,
                                             @RequestParam(value = "offset", required = true) int offset) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        List<SmartUser> usersWithOffset = smartUserService.findUsersWithOffset(offset, smartUser);
        return Response.createResponse(usersWithOffset);
    }

    @RequestMapping(value = "/addFriendRequest.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody Response addFriendRequest(Authentication authentication,
                                                   @RequestParam(value = "friendUsername", required = true) String friendUsername) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        smartUserFriendService.addRequestSmartUserFriend(smartUser, friendUsername);
        return Response.createResponse(true);
    }

    @RequestMapping(value = "/removeFriend.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody Response removeFriend(Authentication authentication,
                             @RequestParam(value = "friendUsername", required = true) String friendUsername) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        smartUserFriendService.removeSmartUserFriend(smartUser, friendUsername);
        return Response.createResponse(true);
    }

    @RequestMapping(value = "/acceptFriendRequest.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody Response acceptFriendRequest(Authentication authentication,
                                    @RequestParam(value = "friendUsername", required = true) String friendUsername) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        smartUserFriendService.changeSmartUserFriendType(smartUser, friendUsername,
                ApplicationConstants.USER_FRIEND_FRIENDSHIP_TYPE);
        return Response.createResponse(true);
    }

    @RequestMapping(value = "/blockFriend.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody Response blockFriend(Authentication authentication,
                            @RequestParam(value = "friendUsername", required = true) String friendUuid) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        smartUserFriendService.changeSmartUserFriendType(smartUser, friendUuid,
                ApplicationConstants.USER_FRIEND_BLOCKED_TYPE);
        return Response.createResponse(true);
    }
}
