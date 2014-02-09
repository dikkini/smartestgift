package com.smartestgift.controller;

import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.SmartUserDetailsDAO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by dikkini on 06.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class ProfileController {

    @Autowired
    SmartUserDAO smartUserDAO;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        return new ModelAndView("profile");
    }

    @RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
    public ModelAndView editProfile() {
        return new ModelAndView("editProfile");
    }

    @RequestMapping(value = "/profile/save", method = RequestMethod.POST)
    public String saveProfile(
                             @RequestParam (required = true, value = "firstname") String firstName,
                             @RequestParam (required = true, value = "lastname") String lastName,
                             @RequestParam (required = true, value = "middlename") String middleName,
                             @RequestParam (required = true, value = "birthdate") String birthDate) {
        SmartUserDetails smartUserDetails = (SmartUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SmartUser smartUser = smartUserDetails.getSmartUser();
        smartUser.setFirstName(firstName);
        smartUser.setLastName(lastName);
        smartUser.setMiddleName(middleName);
        try {
            smartUser.setBirthDate(new SimpleDateFormat("MM.dd.yyyy").parse(birthDate));
        } catch (ParseException e) {
            smartUser.setBirthDate(null);
        }
        smartUserDAO.store(smartUser);
        return "redirect:/profile";
    }
}