package com.smartestgift.controller;

import com.google.gson.Gson;
import com.smartestgift.controller.model.Response;
import com.smartestgift.controller.model.SocketMessage;
import com.smartestgift.dao.model.Conversation;
import com.smartestgift.dao.model.Message;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.service.ConversationService;
import com.smartestgift.service.MessageService;
import com.smartestgift.service.SmartUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private SmartUserService smartUserService;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private Gson gson;

    private Map<String, ScheduledFuture<?>> conversationSchedulers = new HashMap<>();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView messages(Authentication authentication) {
        SmartUser userByUsername = smartUserService.findByUsername(authentication.getName());
        List<Conversation> userConversations = conversationService.findConversationsByUser(userByUsername);
        ModelAndView mav = new ModelAndView("users/messages");
        mav.addObject("userConversations", userConversations);
        return mav;
    }

    @RequestMapping(value = "/getConversationMessages", headers="Accept=application/json", method = RequestMethod.GET)
    public @ResponseBody Response getConversationMessages(Authentication authentication,
                                                              @RequestParam(value = "conversationUuid", required = true)
                                                                                              String conversationUuid) {
        Conversation conversationByUuid = conversationService.findConversationByUuid(conversationUuid);
        SmartUser userByUsername = smartUserService.findByUsername(authentication.getName());
        List<Message> messagesInConversation = messageService.findMessagesInConversation(userByUsername, conversationByUuid);
        return Response.createResponse(messagesInConversation);
    }

    @MessageMapping("/setConversation")
    public void getNewConversationMessages(final SocketMessage socketMessage, final Principal p) {
        // TODO add additional security checks using username and active user
        ScheduledFuture<?> scheduledFutures = conversationSchedulers.get(p.getName());
        if (scheduledFutures != null) {
            scheduledFutures.cancel(true);
        }
        ScheduledFuture<?> messagingScheduler = new ConcurrentTaskScheduler().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Message> newMessagesInConversation = messageService.
                            findNewMessagesInConversation(p.getName(), socketMessage.getParam());
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
    public @ResponseBody void sendMessageToUser(Authentication authentication,
                                                @RequestParam(value = "message", required = true) String message,
                                                @RequestParam(value = "conversation-uuid", required = true) String conversationUuid) {
        // TODO add additional security checks using username and active user
        SmartUser userByUsername = smartUserService.findByUsername(authentication.getName());
        messageService.sendMessageToUser(userByUsername, message, conversationUuid);
    }

    @RequestMapping(value = "/createNewConversation", method = RequestMethod.POST)
    public @ResponseBody Response createNewConversation(Authentication authentication,
                                                            @RequestParam(value = "message", required = true)
                                                            String message,
                                                            @RequestParam(value = "username", required = true)
                                                            String username) {
        // TODO add additional security checks using username and active user
        SmartUser userFrom = smartUserService.findByUsername(authentication.getName());
        SmartUser userTo = smartUserService.findByUsername(username);
        Conversation conversation = conversationService.createConversation(userFrom, userTo, message);
        return Response.createResponse(conversation);
    }

    @MessageMapping("/setUnreadCount")
    public void getCountUserUnreadMessages(final Authentication authentication) {
        // TODO add additional security checks using username and active user
        new ConcurrentTaskScheduler().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer countUserUnreadMessages = messageService.findCountUserUnreadMessages(authentication.getName());
                    if (countUserUnreadMessages != 0) {
                        template.convertAndSendToUser(authentication.getName(), "/getUnreadMessagesCount", countUserUnreadMessages);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000);
    }


    @RequestMapping(value = "/stopNewMessagesSchedulers", method = RequestMethod.POST)
    public void stopNewMessagesSchedulers(Authentication authentication) {
        try {
            ScheduledFuture<?> scheduledFutures = conversationSchedulers.get(authentication.getName());
            scheduledFutures.cancel(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
