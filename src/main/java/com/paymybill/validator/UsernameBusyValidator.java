package com.paymybill.validator;

import com.paymybill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * // TODO fill it
 */

@Service
public class UsernameBusyValidator implements ConstraintValidator<UsernameBusy, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UsernameBusy usernameBusy) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null || username.equals("")) {
            return false;
        }

        boolean usernameBusy = userService.isUsernameBusy(username);
        if (usernameBusy) {
            return false;
        }

        return true;
    }
}
