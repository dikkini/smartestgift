package com.smartestgift.controller;

import com.smartestgift.dao.AuthProviderDAO;
import com.smartestgift.dao.RoleDAO;
import com.smartestgift.dao.SmartUserDetailsDAO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.security.UserAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by dikkini on 06.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class SignupController {

    @Autowired
    SmartUserDetailsDAO smartUserDetailsDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    AuthProviderDAO authProviderDAO;

    @Autowired
    UserAuthProvider authProvider;


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signUpPage() {
        return new ModelAndView("signup");
    }

    @RequestMapping(value = "/signup/register", method = RequestMethod.POST)
    public String signUpUser(HttpServletRequest request,
            @RequestParam (required = true, value = "username") String username,
            @RequestParam (required = true, value = "email") String email,
            @RequestParam (required = true, value = "password") String password,
            @RequestParam (required = true, value = "firstName") String firstName,
            @RequestParam (required = false, value = "lastName") String lastName) {

        // TODO проверка всех входных данных

        StandardPasswordEncoder encoder = new StandardPasswordEncoder();
        String passwordEncoded = encoder.encode(password);

        SmartUser smartUser = new SmartUser(null, username, firstName, lastName, null);
        SmartUserDetails smartUserDetails = new SmartUserDetails(smartUser, passwordEncoded, email, null, new Date(),
                roleDAO.findUserRole(), authProviderDAO.findApplicationProvider());
        smartUserDetailsDAO.store(smartUserDetails);

        authProvider.authenticateUser(smartUserDetails, request);

        return "redirect:/profile";
    }

    @RequestMapping(value = "/signup/social", method = RequestMethod.GET)
    public ModelAndView socialSignUpPage(HttpServletRequest request,
                                         @RequestParam(required = true, value = "errors") String[] errors) {
        SmartUserDetails smartUserDetails = (SmartUserDetails) request.getSession().getAttribute("smartUserDetails");
        ModelAndView mav = new ModelAndView("signupSocial");
        mav.addObject("smartUserDetails", smartUserDetails);
        mav.addObject("errors", errors);
        return mav;
    }

    @RequestMapping(value = "/signup/socialRegister", method = RequestMethod.POST)
    public String sociaRegister(HttpServletRequest request,
                                      @RequestParam (required = true, value = "username") String username,
                                      @RequestParam (required = true, value = "email") String email,
                                      @RequestParam (required = true, value = "firstName") String firstName,
                                      @RequestParam (required = false, value = "lastName") String lastName) {

        // TODO проверка всех входных данных
        SmartUserDetails smartUserDetails = (SmartUserDetails) request.getParameterMap().get("smartUserDetails");
        SmartUser smartUser = new SmartUser(null, username, firstName, lastName, null);
        smartUserDetails = new SmartUserDetails(smartUser, null, email, null, new Date(),
                smartUserDetails.getRole(), smartUserDetails.getAuthProvider());
        smartUserDetailsDAO.store(smartUserDetails);

        authProvider.authenticateUser(smartUserDetails, request);

        return "redirect:/profile";
    }
}
