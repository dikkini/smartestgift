package com.smartestgift.controller;

import com.smartestgift.controller.model.Response;
import com.smartestgift.controller.model.SettingsSmartUserDTO;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    public ModelAndView settings(Authentication authentication) {
        SmartUser byUsername = smartUserService.findByUsername(authentication.getName());
        // TODO сделать конструктор (?)
        SettingsSmartUserDTO settingsSmartUserDTO = new SettingsSmartUserDTO();
        settingsSmartUserDTO.setAddress(byUsername.getAddress());
        settingsSmartUserDTO.setAddressVisible(byUsername.isAddressVisible());

        SimpleDateFormat simpledateformat = new SimpleDateFormat(ApplicationConstants.INPUT_DATE_FORMAT_PATTERN);
        if (byUsername.getBirthDate() != null) {
            settingsSmartUserDTO.setBirthDate(simpledateformat.format(byUsername.getBirthDate()));
        }

        settingsSmartUserDTO.setCellPhone(byUsername.getCellPhone());
        settingsSmartUserDTO.setCellphoneVisible(byUsername.isCellPhoneVisible());
        settingsSmartUserDTO.setFirstName(byUsername.getFirstName());
        settingsSmartUserDTO.setLastName(byUsername.getLastName());
        settingsSmartUserDTO.setMiddleName(byUsername.getMiddleName());
        settingsSmartUserDTO.setProfileVisible(byUsername.isProfileVisible());

        return new ModelAndView("users/settings").addObject("settingsSmartUserDTO", settingsSmartUserDTO)
                .addObject("userImageId", byUsername.getFile().getId());
    }

    @RequestMapping(value = "/profile/settings/save.do", method = RequestMethod.POST)
    public String saveSettings(@Valid SettingsSmartUserDTO settingsSmartUserDTO, BindingResult result,
                                               Authentication authentication) {
        if (result.hasErrors()) {
            return "users/settings";
        }

        SmartUser smartUser = smartUserService.findByUsername(authentication.getName());
        smartUser.setFirstName(settingsSmartUserDTO.getFirstName());
        smartUser.setLastName(settingsSmartUserDTO.getLastName());
        smartUser.setMiddleName(settingsSmartUserDTO.getMiddleName());
        smartUser.setAddress(settingsSmartUserDTO.getAddress());
        smartUser.setAddressVisible(settingsSmartUserDTO.isAddressVisible());
        smartUser.setCellPhone(settingsSmartUserDTO.getCellPhone());
        smartUser.setCellPhoneVisible(settingsSmartUserDTO.isCellphoneVisible());
        smartUser.setProfileVisible(settingsSmartUserDTO.isProfileVisible());

        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(ApplicationConstants.INPUT_DATE_FORMAT_PATTERN);
            Date tempDate = simpledateformat.parse(settingsSmartUserDTO.getBirthDate());
            smartUser.setBirthDate(tempDate);
        } catch (ParseException e) {
            smartUser.setBirthDate(null);
        }

        smartUserService.update(smartUser);
        return "users/profile";
    }
}