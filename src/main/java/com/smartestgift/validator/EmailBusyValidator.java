package com.smartestgift.validator;

import com.smartestgift.service.SmartUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * // TODO fill it
 */

@Service
public class EmailBusyValidator implements ConstraintValidator<EmailBusy, String> {

    @Autowired
    private SmartUserService smartUserService;

    @Override
    public void initialize(EmailBusy emailBusy) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null || username.equals("")) {
            return false;
        }

        boolean usernameBusy = smartUserService.isEmailBusy(username);
        if (usernameBusy) {
            return false;
        }

        return true;
    }
}
