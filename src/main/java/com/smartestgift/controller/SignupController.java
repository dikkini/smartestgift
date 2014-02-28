package com.smartestgift.controller;

import com.smartestgift.controller.model.AjaxResponse;
import com.smartestgift.dao.AuthProviderDAO;
import com.smartestgift.dao.RoleDAO;
import com.smartestgift.dao.SmartUserDetailsDAO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.security.UserAuthProvider;
import com.smartestgift.service.SmartUserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dikkini on 06.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    SmartUserDetailsDAO smartUserDetailsDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    AuthProviderDAO authProviderDAO;

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
        // TODO проверка всех входных данных

        StandardPasswordEncoder encoder = new StandardPasswordEncoder();
        String passwordEncoded = encoder.encode(password);

        SmartUser smartUser = new SmartUser(null, username, firstName, lastName, null, null);
        SmartUserDetails smartUserDetails = new SmartUserDetails(smartUser, passwordEncoded, email, null, new Date(),
                roleDAO.findUserRole(), authProviderDAO.findApplicationProvider());
        smartUserDetailsDAO.store(smartUserDetails);

        authProvider.authenticateUser(smartUserDetails, request);

        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/social", method = RequestMethod.GET)
    public ModelAndView socialSignUpPage(HttpServletRequest request,
                                         @RequestParam (required = true, value = "id") String socialId,
                                         @RequestParam(required = true, value = "errors") String[] errors) {
        SmartUserDetails smartUserDetails = (SmartUserDetails) request.getSession().getAttribute(socialId);
        ModelAndView mav = new ModelAndView("signupSocial");
        mav.addObject("smartUserDetails", smartUserDetails);
        mav.addObject("errors", errors);
        return mav;
    }

    @RequestMapping(value = "/social/register", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse socialRegister(HttpServletRequest request,
                                      @RequestParam (required = true, value = "id") String socialId,
                                      @RequestParam (required = true, value = "username") String username,
                                      @RequestParam (required = true, value = "email") String email,
                                      @RequestParam (required = true, value = "firstName") String firstName,
                                      @RequestParam (required = false, value = "lastName") String lastName) {

        AjaxResponse response = new AjaxResponse();

        // TODO проверка всех входных данных
        SmartUserDetails smartUserDetails = (SmartUserDetails) request.getSession().getAttribute(socialId);
        SmartUser smartUser = new SmartUser(null, username, firstName, lastName, null, null);
        smartUserDetails = new SmartUserDetails(smartUser, null, email, null, new Date(),
                smartUserDetails.getRole(), smartUserDetails.getAuthProvider());
        smartUserDetailsDAO.store(smartUserDetails);

        authProvider.authenticateUser(smartUserDetails, request);

        response.setSuccess(true);
        return response;
    }

    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse checkLogin(@RequestParam(value = "login", required = true) String login) {
        boolean loginFree = smartUserService.checkOccupiedUserLogin(login);
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
