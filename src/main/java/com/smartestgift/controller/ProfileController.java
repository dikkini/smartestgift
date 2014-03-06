package com.smartestgift.controller;

import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.utils.ActiveUser;
import com.smartestgift.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        return new ModelAndView("users/profile");
    }

    @RequestMapping(value = "/profile/settings", method = RequestMethod.GET)
    public ModelAndView settings() {
        return new ModelAndView("users/settings");
    }

    @RequestMapping(value = "/profile/settings/save", method = RequestMethod.POST)
    public String saveSettings(@ActiveUser SmartUserDetails smartUserDetails,
            @RequestParam(required = true, value = "firstName") String firstName,
            @RequestParam(required = true, value = "lastName") String lastName,
            @RequestParam(required = true, value = "middleName") String middleName,
            @RequestParam(required = true, value = "birthdate") String birthDate,
            @RequestParam(required = true, value = "address") String address,
            @RequestParam(required = false, value = "addressVisible") boolean addressVisible,
            @RequestParam(required = false, value = "profileVisible") boolean profileVisible,
            @RequestParam(required = true, value = "cellphone") String cellPhone,
            @RequestParam(required = false, value = "cellphoneVisible") boolean cellphoneVisible) {
        SmartUser smartUser = smartUserDetails.getSmartUser();
        smartUser.setFirstName(firstName);
        smartUser.setLastName(lastName);
        smartUser.setMiddleName(middleName);
        smartUser.setAddress(address);
        smartUser.setAddressVisible(addressVisible);
        smartUser.setCellPhone(cellPhone);
        smartUser.setCellPhoneVisible(cellphoneVisible);
        smartUser.setProfileVisible(profileVisible);
        try {

            SimpleDateFormat simpledateformat = new SimpleDateFormat(ApplicationConstants.INPUT_DATE_FORMAT_PATTERN);
            Date tempDate = simpledateformat.parse(birthDate);
            smartUser.setBirthDate(tempDate);
        } catch (ParseException e) {
            smartUser.setBirthDate(null);
        }
        smartUserDAO.store(smartUser);
        return "redirect:/profile";
    }
}