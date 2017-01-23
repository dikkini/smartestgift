package com.paymybill.validator;


import com.paymybill.controller.model.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsEqualConstraintValidator implements ConstraintValidator<PasswordsEqualConstraint, Object> {

    @Override
    public void initialize(PasswordsEqualConstraint arg0) {}

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {
        UserDTO user = (UserDTO) candidate;
        return user.getPassword().equals(user.getRepeatPassword());
    }
}