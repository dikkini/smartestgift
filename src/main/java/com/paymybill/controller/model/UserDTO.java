package com.paymybill.controller.model;

import com.paymybill.validator.EmailBusy;
import com.paymybill.validator.PasswordsEqualConstraint;
import com.paymybill.validator.UsernameBusy;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordsEqualConstraint(message = "{validate.passwordEquals}")
public class UserDTO {

    @NotNull(message = "{validate.required}")
    @Size(min = 3, max = 30, message = "{validate.range}")
    @UsernameBusy(message = "{validate.usernameBusy}")
    private String username;

    @NotNull(message = "{validate.required}")
    @Size(min = 3, max = 30, message = "{validate.range}")
    private String firstName;

    @NotNull(message = "{validate.required}")
    @Size(min = 6, max = 30, message = "{validate.range}")
    private String password;

    @NotNull(message = "{validate.required}")
    @Size(min = 6, max = 30, message = "{validate.range}")
    private String repeatPassword;

    @NotNull(message = "{validate.required}")
    @Size(min = 3, max = 30, message = "{validate.range}")
    @EmailBusy(message = "{validate.emailBusy}")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{validate{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
