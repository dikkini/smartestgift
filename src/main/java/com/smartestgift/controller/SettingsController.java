package com.smartestgift.controller;

import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dikkini on 10.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
public class SettingsController {

    @Autowired
    SmartUserDAO smartUserDAO;

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView settings() {
        return new ModelAndView("settings");
    }

    @RequestMapping(value = "/settings/save", method = RequestMethod.POST)
    public String saveSettings(
            @RequestParam(required = true, value = "firstName") String firstName,
            @RequestParam(required = true, value = "lastName") String lastName,
            @RequestParam(required = true, value = "middleName") String middleName,
            @RequestParam(required = true, value = "birthdate") String birthDate,
            @RequestParam(required = true, value = "address") String address,
            @RequestParam(required = true, value = "addressVisible") boolean addressVisible,
            @RequestParam(required = true, value = "profileVisible") boolean profileVisible,
            @RequestParam(required = true, value = "cellphone") String cellPhone,
            @RequestParam(required = true, value = "cellphoneVisible") boolean cellphoneVisible) {
        SmartUserDetails smartUserDetails = (SmartUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
            Date tempDate = simpledateformat.parse(birthDate);
            smartUser.setBirthDate(tempDate);
        } catch (ParseException e) {
            smartUser.setBirthDate(null);
        }
        smartUserDAO.store(smartUser);
        return "redirect:/settings";
    }
}
