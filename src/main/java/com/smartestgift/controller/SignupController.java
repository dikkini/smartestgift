package com.smartestgift.controller;

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

    @Autowired
    SmartUserService smartUserService;


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
                                         @RequestParam (required = true, value = "id") String socialId,
                                         @RequestParam(required = true, value = "errors") String[] errors) {
        SmartUserDetails smartUserDetails = (SmartUserDetails) request.getSession().getAttribute(socialId);
        ModelAndView mav = new ModelAndView("signupSocial");
        mav.addObject("smartUserDetails", smartUserDetails);
        mav.addObject("errors", errors);
        return mav;
    }

    @RequestMapping(value = "/signup/socialRegister", method = RequestMethod.POST)
    public String socialRegister(HttpServletRequest request,
                                      @RequestParam (required = true, value = "id") String socialId,
                                      @RequestParam (required = true, value = "username") String username,
                                      @RequestParam (required = true, value = "email") String email,
                                      @RequestParam (required = true, value = "firstName") String firstName,
                                      @RequestParam (required = false, value = "lastName") String lastName) {

        // TODO проверка всех входных данных
        SmartUserDetails smartUserDetails = (SmartUserDetails) request.getSession().getAttribute(socialId);
        SmartUser smartUser = new SmartUser(null, username, firstName, lastName, null);
        smartUserDetails = new SmartUserDetails(smartUser, null, email, null, new Date(),
                smartUserDetails.getRole(), smartUserDetails.getAuthProvider());
        smartUserDetailsDAO.store(smartUserDetails);

        authProvider.authenticateUser(smartUserDetails, request);

        return "redirect:/profile";
    }

    @RequestMapping(value = "/login/check", method = RequestMethod.POST)
    public @ResponseBody
    String checkLogin(@RequestParam(value = "login", required = true) String login) {
        boolean free = smartUserService.checkOccupiedUserLogin(login);
        JSONObject result = new JSONObject();
        try {
            result.put("free", free);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
