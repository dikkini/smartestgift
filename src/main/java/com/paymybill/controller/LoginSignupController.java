package com.paymybill.controller;

import com.paymybill.controller.model.UserDTO;
import com.paymybill.dao.model.Privilege;
import com.paymybill.dao.model.User;
import com.paymybill.exception.EmailExistException;
import com.paymybill.exception.UsernameExistException;
import com.paymybill.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginSignupController {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext context;

    @GetMapping
    @RequestMapping("/login")
    public ModelAndView loginPage() {
        logger.trace("login page");
        return new ModelAndView("login");
    }

    @GetMapping
    @RequestMapping("/signup")
    public ModelAndView signupPage() {
        logger.trace("signup page");
        ModelAndView mav = new ModelAndView("signup");
        mav.addObject("registerUser", new UserDTO());
        return mav;
    }

    @PostMapping(value = "/signup")
    public ModelAndView signupAction(@Valid @ModelAttribute("registerUser") UserDTO userDTO, BindingResult result) {
        logger.trace("signup action");
        int errorCount = result.getErrorCount();

        ModelAndView mav = new ModelAndView();

        if (errorCount != 0) {
            mav.setViewName("signup");
            mav.addObject("SIGNUP_ERROR", context.getMessage("validate.common", null, LocaleContextHolder.getLocale()));
            mav.addObject("registerUser", userDTO);
            return mav;
        }

        User user;
        try {
            user = userService.registerUser(userDTO);
            authenticateUser(user);

            mav.setViewName("redirect:/");
        } catch (UsernameExistException e) {
            mav.setViewName("signup");
            mav.addObject("registerUser", userDTO);
            mav.addObject("SIGNUP_ERROR", context.getMessage("UsernameExistException", null, LocaleContextHolder.getLocale()));
        } catch (EmailExistException e) {
            mav.setViewName("signup");
            mav.addObject("registerUser", userDTO);
            mav.addObject("SIGNUP_ERROR", context.getMessage("EmailExistException", null, LocaleContextHolder.getLocale()));
        }

        return mav;
    }

    private void authenticateUser(User user) {
        logger.trace("authenticateUser");
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (final Privilege privilege : user.getRole().getPrivileges()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(privilege.getName()));
        }

        Authentication auth =
                new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
