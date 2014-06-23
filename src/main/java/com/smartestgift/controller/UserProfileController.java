package com.smartestgift.controller;

import com.google.gson.Gson;
import com.smartestgift.controller.model.Response;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserFriend;
import com.smartestgift.exception.BadUserException;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

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
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        List<SmartUserFriend> allSmartUserFriends = smartUserService.findAllSmartUserFriends(smartUser);
        return new ModelAndView("users/myfriends").addObject("smartUserFriends", allSmartUserFriends);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ModelAndView user(@PathVariable String username) {
        ModelAndView mav;
        SmartUser smartUser = smartUserDAO.findSmartUserByUsername(username);
        if (smartUser == null) {
            throw new BadUserException("Bad user.", ApplicationConstants.INTERNAL_EXCEPTION_MESSAGE);
        }

        mav = new ModelAndView("users/user");
        mav.addObject("alienSmartUser", smartUser);

        return mav;
    }

    @RequestMapping(value = "/findPeople.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody Response findPeople(Authentication authentication,
                                                    @RequestParam(value = "offset", required = true) int offset) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        List<SmartUser> usersWithOffset = smartUserService.findUsersWithOffset(offset, smartUser);
        return Response.createResponse(usersWithOffset);
    }

    @RequestMapping(value = "/addFriendRequest.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public void addFriendRequest(Authentication authentication,
                                 @RequestParam(value = "friendUsername", required = true) String friendUsername) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        smartUserService.addRequestSmartUserFriend(smartUser, friendUsername);
    }

    @RequestMapping(value = "/removeFriend.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public void removeFriend(Authentication authentication,
                             @RequestParam(value = "friendUsername", required = true) String friendUsername) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        smartUserService.removeSmartUserFriend(smartUser, friendUsername);
    }

    @RequestMapping(value = "/acceptFriendRequest.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public void acceptFriendRequest(Authentication authentication,
                                    @RequestParam(value = "friendUsername", required = true) String friendUsername) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        smartUserService.changeSmartUserFriendType(smartUser, friendUsername,
                ApplicationConstants.USER_FRIEND_FRIEND_TYPE);
    }

    @RequestMapping(value = "/declineFriendRequest.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public void declineFriendRequest(Authentication authentication,
                                     @RequestParam(value = "friendUsername", required = true) String friendUsername) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        smartUserService.removeSmartUserFriend(smartUser, friendUsername);
    }

    @RequestMapping(value = "/blockFriend.do", headers = "Accept=application/json", method = RequestMethod.POST)
    public void blockFriend(Authentication authentication,
                            @RequestParam(value = "friendUuid", required = true) String friendUuid) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
        smartUserService.changeSmartUserFriendType(smartUser, friendUuid,
                ApplicationConstants.USER_FRIEND_BLOCK_TYPE);
    }
}
