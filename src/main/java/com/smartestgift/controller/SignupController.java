package com.smartestgift.controller;

import com.restfb.types.User;
import com.smartestgift.controller.model.AjaxResponse;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.security.UserAuthProvider;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ApplicationConstants;
import com.smartestgift.utils.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by dikkini on 06.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    UserAuthProvider authProvider;

    @Autowired
    SmartUserService smartUserService;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView signUpPage() {
        return new ModelAndView("signup");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse signUpUser(HttpServletRequest request,
            @RequestParam (required = true, value = "username") String username,
            @RequestParam (required = true, value = "email") String email,
            @RequestParam (required = true, value = "password") String password,
            @RequestParam (required = true, value = "firstName") String firstName,
            @RequestParam (required = false, value = "lastName") String lastName) {

        AjaxResponse result = new AjaxResponse();

        boolean emailOccupied = smartUserService.checkOccupiedEmail(email);
        boolean usernameOccupied = smartUserService.checkOccupiedUsername(username);

        if (!emailOccupied) {
            result.addError(ResponseMessages.EMAIL_EXISTING_ERROR);
        }

        if (!usernameOccupied) {
            result.addError(ResponseMessages.USERNAME_EXISTING_ERROR);
        }

        if (result.getErrors().size() > 0) {
            result.setSuccess(false);
            return result;
        }

        StandardPasswordEncoder encoder = new StandardPasswordEncoder();
        String passwordEncoded = encoder.encode(password);

        SmartUserDetails newUser = smartUserService.createNewUser(username, email, passwordEncoded, firstName, lastName,
                ApplicationConstants.APPLICATION_AUTH_PROVIDER_ID, ApplicationConstants.USER_ROLE_ID);

        authProvider.authenticateUser(newUser, request);

        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public ModelAndView socialSignUpPage(HttpServletRequest request,
                                         @RequestParam (required = true, value = "id") String socialId,
                                         @RequestParam(required = true, value = "errors") String[] errors) {
        SmartUserDetails smartUserDetails = (SmartUserDetails) request.getSession().getAttribute(socialId);
        ModelAndView mav = new ModelAndView("signupSocial");
        mav.addObject("smartUserDetails", smartUserDetails);
        mav.addObject("errors", errors);
        return mav;
    }

    @RequestMapping(value = "/facebook/register", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse socialRegister(HttpServletRequest request,
                                      @RequestParam (required = true, value = "id") String socialId,
                                      @RequestParam (required = true, value = "username") String username,
                                      @RequestParam (required = true, value = "email") String email,
                                      @RequestParam (required = true, value = "firstName") String firstName,
                                      @RequestParam (required = false, value = "lastName") String lastName) {
        // TODO проверка всех входных данных
        AjaxResponse response = new AjaxResponse();

        User facebookUser = (User) request.getSession().getAttribute(socialId);
        SmartUserDetails newUserFromFacebook = smartUserService.createNewUserFromFacebook(facebookUser, username,
                email, firstName, lastName, socialId);
        authProvider.authenticateUser(newUserFromFacebook, request);

        response.setSuccess(true);
        return response;
    }

    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse checkLogin(@RequestParam(value = "login", required = true) String login) {
        boolean loginFree = smartUserService.checkOccupiedUsername(login);
        AjaxResponse result = new AjaxResponse();
        result.setSuccess(loginFree);
        return result;
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse checkEmail(@RequestParam(value = "email", required = true) String email) {
        boolean emailFree = smartUserService.checkOccupiedEmail(email);
        AjaxResponse result = new AjaxResponse();
        result.setSuccess(emailFree);
        return result;
    }
}
