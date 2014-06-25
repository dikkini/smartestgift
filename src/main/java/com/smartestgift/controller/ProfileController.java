package com.smartestgift.controller;

import com.smartestgift.controller.model.Response;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    private SmartUserService smartUserService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(Authentication authentication) {
        return new ModelAndView("users/profile");
    }

    @RequestMapping(value = "/profile/settings", method = RequestMethod.GET)
    public ModelAndView settings() {
        return new ModelAndView("users/settings");
    }

    @RequestMapping(value = "/profile/settings/save.do", method = RequestMethod.PUT)
    public @ResponseBody Response saveSettings(Authentication authentication,
                                               @RequestParam(required = true, value = "firstName") String firstName,
                                               @RequestParam(required = true, value = "lastName") String lastName,
                                               @RequestParam(required = true, value = "middleName") String middleName,
                                               @RequestParam(required = true, value = "birthdate") String birthDate,
                                               @RequestParam(required = true, value = "address") String address,
                                               @RequestParam(required = false, value = "addressVisible") boolean addressVisible,
                                               @RequestParam(required = false, value = "profileVisible") boolean profileVisible,
                                               @RequestParam(required = true, value = "cellphone") String cellPhone,
                                               @RequestParam(required = false, value = "cellphoneVisible") boolean cellphoneVisible) {
        SmartUser smartUser = smartUserService.findUserByUsername(authentication.getName());
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
        smartUserService.createSmartUser(smartUser);
        return Response.createResponse(true);
    }
}