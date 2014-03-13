package com.smartestgift.controller;

import com.smartestgift.controller.model.AjaxResponse;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.service.ConversationService;
import com.smartestgift.service.MessageService;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    ConversationService conversationService;

    @Autowired
    MessageService messageService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView messages(@ActiveUser SmartUserDetails smartUserDetails) {
        List<Conversation> userConversations = conversationService.findUserConversations(smartUserDetails.getSmartUser());
        ModelAndView mav = new ModelAndView("users/messages");
        mav.addObject("userConversations", userConversations);
        return mav;
    }

    @RequestMapping(value = "/getConversationMessages", method = RequestMethod.POST)
    public @ResponseBody List<Message> getMessagesWithUser(@ActiveUser SmartUserDetails smartUserDetails,
                                                           @RequestParam(value = "conversationUuid", required = true)
                                                           String conversationUuid) {
        // TODO add additional security checks using username and active user
        Conversation conversation = conversationService.findConversationByUuid(conversationUuid);
        List<Message> userMessagesWithUser = messageService.findConversationMessages(conversation);
        return userMessagesWithUser;
    }

    @RequestMapping(value = "/sendMessageToUser", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse sendMessageToUser(@ActiveUser SmartUserDetails smartUserDetails,
                                                        @RequestParam(value = "message", required = true) String message,
                                                        @RequestParam(value = "conversation-uuid", required = true) String conversationUuid) {
        // TODO add additional security checks using username and active user
        AjaxResponse result = new AjaxResponse();
        messageService.sendMessageToUser(smartUserDetails.getUsername(), message, conversationUuid);
        result.setSuccess(true);
        result.addSuccessMessage("message_sent_success");
        return result;
    }

    @RequestMapping(value = "/createNewConversation", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse createNewConversation(@ActiveUser SmartUserDetails smartUserDetails,
                                                                 @RequestParam(value = "message", required = true) String message,
                                                                 @RequestParam(value = "username", required = true) String username) {
        // TODO add additional security checks using username and active user
        AjaxResponse result = new AjaxResponse();
        messageService.createConversation(smartUserDetails.getUsername(), username, message);
        result.setSuccess(true);
        //TODO добавить в сообщения данное сообщение
        result.addSuccessMessage("message_sent_success");
        return result;
    }

    @RequestMapping(value = "/getCountUserMessages", method = RequestMethod.POST)
    public @ResponseBody Integer getCountUserMessages(@ActiveUser SmartUserDetails smartUserDetails) {
        // TODO add additional security checks using username and active user
        Integer countUserMessages = messageService.getCountUserMessages(smartUserDetails.getUsername());
        return countUserMessages;
    }
}
