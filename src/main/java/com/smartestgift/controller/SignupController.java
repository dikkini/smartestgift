package com.smartestgift.controller;

import com.smartestgift.controller.model.RegisterSmartUserDTO;
import com.smartestgift.controller.model.Response;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.exception.EmailBusyException;
import com.smartestgift.exception.UsernameBusyException;
import com.smartestgift.service.SmartUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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
        return new ModelAndView("register/signup").addObject("registerUserDTO", new RegisterSmartUserDTO());
    }

    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    public @ResponseBody Response signUpUser(@Valid @ModelAttribute("registerUserDTO") RegisterSmartUserDTO registerUserDTO, BindingResult result,
                                             RedirectAttributes attributes, HttpServletRequest request) {

        boolean emailOccupied = smartUserService.isEmailBusy(registerUserDTO.getEmail());
        boolean usernameOccupied = smartUserService.isUsernameBusy(registerUserDTO.getUsername());

        List<FieldError> fieldErrors = result.getFieldErrors();
        String code = fieldErrors.get(0).getCode();

        if (usernameOccupied) {
            throw new UsernameBusyException("Username is busy", HttpStatus.IM_USED.value());
        } else if (emailOccupied) {
            throw new EmailBusyException("Email is busy", HttpStatus.IM_USED.value());
        }

        StandardPasswordEncoder encoder = new StandardPasswordEncoder();
        String passwordEncoded = encoder.encode(registerUserDTO.getPassword());
        registerUserDTO.setPassword(passwordEncoded);
        SmartUser registerUser = smartUserService.create(registerUserDTO);

        smartUserService.createUserAuthority(registerUser.getUsername());
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

        smartUserService.create(facebookUser);
        smartUserService.createUserAuthority(facebookUser.getUsername());
        smartUserService.authenticateUser(facebookUser.getUsername(), facebookUser.getPassword(), request);

        return Response.createResponse(true);
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
