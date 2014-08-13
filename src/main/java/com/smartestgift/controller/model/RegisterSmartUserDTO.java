package com.smartestgift.controller.model;

import com.smartestgift.utils.ApplicationConstants;
import com.smartestgift.validator.EmailBusy;
import com.smartestgift.validator.UsernameBusy;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.*;

import java.util.Date;

/**
 * // TODO fill it
 */
public class RegisterSmartUserDTO {

    @UsernameBusy
    @NotEmpty(message = "{NotEmpty.registerSmartUserDTO.username}")
    @Length(min = 3, max = 100, message = "{Length.message}")
    protected String username;

    @EmailBusy
    @NotEmpty(message = "{NotEmpty.registerSmartUserDTO.email}")
    @Email(message = "{Email.message}")
    protected String email;

    @NotEmpty(message = "{NotEmpty.registerSmartUserDTO.password}")
    protected String password;

    @NotEmpty(message = "{NotEmpty.registerSmartUserDTO.firstName}")
    protected String firstName;

    @Length(min = 3, max = 100, message = "{Length.message}")
    protected String lastName;

    @NotEmpty(message = "{NotEmpty.registerSmartUserDTO.city}")
    protected String city;

    protected Integer authProviderId = ApplicationConstants.APPLICATION_AUTH_PROVIDER_ID;

    protected Date registrationDate = new Date();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAuthProviderId() {
        return authProviderId;
    }

    public void setAuthProviderId(Integer authProviderId) {
        this.authProviderId = authProviderId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
