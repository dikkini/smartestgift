package com.smartestgift.controller.model;

import com.smartestgift.utils.ApplicationConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

/**
 * // TODO fill it
 */
public class RegisterSocialUserDTO {

    @NotEmpty
    @Range(min = 3, max = 100)
    protected String username;

    @NotEmpty
    @Email
    protected String email;

    @NotEmpty
    protected String firstName;

    @Range(min = 3, max = 100)
    protected String lastName;

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

