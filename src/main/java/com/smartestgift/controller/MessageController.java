package com.smartestgift.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartestgift.controller.model.AjaxResponse;
import com.smartestgift.controller.model.SocketMessage;
import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.service.ConversationService;
import com.smartestgift.service.MessageService;
import com.smartestgift.utils.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.persistence.PostLoad;
import java.lang.reflect.Type;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by dikkini on 10.03.14.
 * Email: dikkini@gmail.com
 */

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private Gson gson;

    private Map<String, ScheduledFuture<?>> conversationSchedulers = new HashMap<>();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView messages(@ActiveUser SmartUserDetails smartUserDetails) {
        List<Conversation> userConversations = conversationService.findConversationsByUser(smartUserDetails.getSmartUser());
        ModelAndView mav = new ModelAndView("users/messages");
        mav.addObject("userConversations", userConversations);
        return mav;
    }

    @RequestMapping(value = "/getConversationMessages", headers="Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody String getConversationMessages(@ActiveUser SmartUserDetails smartUserDetails,
                                                              @RequestParam(value = "conversationUuid", required = true)
                                                                                              String conversationUuid) {
        // TODO add additional security checks using username and active user
        List<Message> messagesInConversation = messageService.findAllMessagesByConversationForUser(smartUserDetails.getUsername(),
                conversationUuid);
        return gson.toJson(messagesInConversation);
    }

    @MessageMapping("/setConversation")
    public void getNewConversationMessages(final SocketMessage socketMessage, final Principal p) {
        // TODO add additional security checks using username and active user
        ScheduledFuture<?> scheduledFuture = conversationSchedulers.get(p.getName());
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        ScheduledFuture<?> messagingScheduler = new ConcurrentTaskScheduler().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Message> newMessagesInConversation = messageService.
                            findNewMessagesForUserByConversation(p.getName(), socketMessage.getParam());
                    if (newMessagesInConversation.size() > 0) {
                        String json = gson.toJson(newMessagesInConversation);
                        template.convertAndSendToUser(p.getName(), "/getNewConversationMessages", json);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1500);
        conversationSchedulers.put(p.getName(), messagingScheduler);
    }

    @RequestMapping(value = "/sendMessageToUser", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse sendMessageToUser(@ActiveUser SmartUserDetails smartUserDetails,
                                                        @RequestParam(value = "message", required = true)
                                                        String message,
                                                        @RequestParam(value = "conversation-uuid", required = true)
                                                        String conversationUuid) {
        // TODO add additional security checks using username and active user
        AjaxResponse result = new AjaxResponse();
        messageService.sendMessageToUser(smartUserDetails.getSmartUser(), message, conversationUuid);
        result.setSuccess(true);
        result.addSuccessMessage("message_sent_success");
        return result;
    }

    @RequestMapping(value = "/createNewConversation", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse createNewConversation(@ActiveUser SmartUserDetails smartUserDetails,
                                                                 @RequestParam(value = "message", required = true)
                                                                 String message,
                                                                 @RequestParam(value = "username", required = true)
                                                                 String username) {
        // TODO add additional security checks using username and active user
        AjaxResponse result = new AjaxResponse();
        conversationService.createConversation(smartUserDetails.getSmartUser(), username, message);
        result.setSuccess(true);
        //TODO добавить в сообщения данное сообщение
        result.addSuccessMessage("message_sent_success");
        return result;
    }

    @MessageMapping("/setUnreadCount")
    public void getCountUserUnreadMessages(final Principal p) {
        // TODO add additional security checks using username and active user
        new ConcurrentTaskScheduler().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer countUserUnreadMessages = messageService.findCountUnreadMessages(p.getName());
                    if (countUserUnreadMessages != 0) {
                        template.convertAndSendToUser(p.getName(), "/getUnreadMessagesCount", countUserUnreadMessages);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5000);
    }


    @RequestMapping(value = "/stopNewMessagesSchedulers", method = RequestMethod.POST)
    public
    @ResponseBody
    AjaxResponse stopNewMessagesSchedulers(@ActiveUser SmartUserDetails smartUserDetails) {
        AjaxResponse result = new AjaxResponse();
        try {
            ScheduledFuture<?> scheduledFuture = conversationSchedulers.get(smartUserDetails.getUsername());
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
            }
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }
}
