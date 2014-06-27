package com.smartestgift.controller;

import com.smartestgift.controller.model.Response;
import com.smartestgift.dao.model.File;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.exception.EmailBusyException;
import com.smartestgift.exception.UsernameBusyException;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by dikkini on 06.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private SmartUserService smartUserService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView signUpPage(HttpServletRequest request) {
        request.setAttribute("social", false);
        return new ModelAndView("signup");
    }

    @RequestMapping(value = "/register", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody Response signUpUser(HttpServletRequest request,
                                             @RequestParam (required = true, value = "username") String username,
                                             @RequestParam (required = true, value = "email") String email,
                                             @RequestParam (required = true, value = "password") String password,
                                             @RequestParam (required = true, value = "firstName") String firstName,
                                             @RequestParam (required = false, value = "lastName") String lastName) {

        boolean emailOccupied = smartUserService.checkOccupiedEmail(email);
        boolean usernameOccupied = smartUserService.checkOccupiedUsername(username);

        if (usernameOccupied) {
            throw new UsernameBusyException("Username is busy", HttpStatus.IM_USED.value());
        } else if (emailOccupied) {
            throw new EmailBusyException("Email is busy", HttpStatus.IM_USED.value());
        }

        StandardPasswordEncoder encoder = new StandardPasswordEncoder();
        String passwordEncoded = encoder.encode(password);

        SmartUser registerUser = smartUserService.createSmartUser(username, passwordEncoded, email, lastName, firstName,
                new Date(), ApplicationConstants.APPLICATION_AUTH_PROVIDER_ID, true);

        smartUserService.createUserAuthorityForUser(registerUser.getUsername());
        smartUserService.authenticateUser(registerUser.getUsername(), registerUser.getPassword(), request);

        return Response.createResponse(true);
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public ModelAndView socialSignUpPage(HttpServletRequest request,
                                         @RequestParam (required = true, value = "id") String socialId,
                                         @RequestParam(required = true, value = "email_error") boolean email_error,
                                         @RequestParam(required = true, value = "username_error") boolean username_error) {
        SmartUser smartUser = (SmartUser) request.getSession().getAttribute(socialId);

        request.setAttribute("social", true);
        ModelAndView mav = new ModelAndView("signupSocial");
        mav.addObject("facebookSmartUser", smartUser);
        mav.addObject("email_error", email_error);
        mav.addObject("username_error", username_error);
        return mav;
    }

    @RequestMapping(value = "/facebook/register", headers = "Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody Response socialRegister(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestParam (required = true, value = "id") String socialId,
                                                 @RequestParam (required = true, value = "username") String username,
                                                 @RequestParam (required = true, value = "email") String email,
                                                 @RequestParam (required = true, value = "firstName") String firstName,
                                                 @RequestParam (required = false, value = "lastName") String lastName) {
        // TODO проверка всех входных данных
        // TODO обработка города из КЛАДРа
        SmartUser facebookUser = (SmartUser) request.getSession().getAttribute(socialId);
        facebookUser.setUsername(username);
        facebookUser.setEmail(email);
        facebookUser.setFirstName(firstName);
        facebookUser.setLastName(lastName);

        smartUserService.createSmartUser(facebookUser);
        smartUserService.authenticateUser(facebookUser.getUsername(), facebookUser.getPassword(), request);

        return Response.createResponse(true);
    }

    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    public @ResponseBody Response checkLogin(@RequestParam(value = "login", required = true) String login) {
        boolean loginFree = smartUserService.checkOccupiedUsername(login);
        return Response.createResponse(loginFree);
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
    public @ResponseBody Response checkEmail(@RequestParam(value = "email", required = true) String email) {
        boolean emailFree = smartUserService.checkOccupiedEmail(email);
        return Response.createResponse(emailFree);
    }

    @ExceptionHandler(UsernameBusyException.class)
    public @ResponseBody ResponseEntity handleUsernameBusyException(HttpServletResponse response) {
        return new ResponseEntity<String>(HttpStatus.IM_USED);
    }

    @ExceptionHandler(EmailBusyException.class)
    public @ResponseBody ResponseEntity handleEmailBusyException(HttpServletResponse response) {
        return new ResponseEntity<String>(HttpStatus.IM_USED);
    }
}
