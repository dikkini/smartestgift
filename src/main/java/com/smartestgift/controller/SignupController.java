package com.smartestgift.controller;

import com.smartestgift.controller.model.RegisterSmartUserDTO;
import com.smartestgift.controller.model.Response;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.exception.EmailBusyException;
import com.smartestgift.exception.UsernameBusyException;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
        return new ModelAndView("register/signup").addObject("registerSmartUserDTO", new RegisterSmartUserDTO());
    }

    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    public String signUpUser(@Valid RegisterSmartUserDTO registerSmartUserDTO, BindingResult result,
                             HttpServletRequest request) {

        if (result.hasErrors()) {
            return "register/signup";
        }

        StandardPasswordEncoder encoder = new StandardPasswordEncoder();
        String passwordEncoded = encoder.encode(registerSmartUserDTO.getPassword());
        registerSmartUserDTO.setPassword(passwordEncoded);
        SmartUser registerUser = smartUserService.create(registerSmartUserDTO);

        smartUserService.createUserAuthority(registerUser.getUsername());
        smartUserService.authenticateUser(registerUser.getUsername(), registerUser.getPassword(), request);

        return Utils.createRedirectViewPath("/profile");
    }

    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    public @ResponseBody Response checkLogin(@RequestParam(value = "login", required = true) String login) {
        boolean usernameBusy = smartUserService.isUsernameBusy(login);
        return Response.createResponse(!usernameBusy);
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
    public @ResponseBody Response checkEmail(@RequestParam(value = "email", required = true) String email) {
        boolean emailBusy = smartUserService.isEmailBusy(email);
        return Response.createResponse(!emailBusy);
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
