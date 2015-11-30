package com.smartestgift.controller.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.*;

import java.util.Date;

/**
 * // TODO fill it
 */
public class SettingsSmartUserDTO {

    @NotEmpty(message = "{NotEmpty.firstName}")
    protected String firstName;

    protected String lastName;

    protected String middleName;

    protected String birthDate;

    @NotEmpty(message = "{NotEmpty.address}")
    protected String address;

    protected boolean addressVisible;

    protected boolean profileVisible;

    protected String cellPhone;

    protected boolean cellphoneVisible;

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAddressVisible() {
        return addressVisible;
    }

    public void setAddressVisible(boolean addressVisible) {
        this.addressVisible = addressVisible;
    }

    public boolean isProfileVisible() {
        return profileVisible;
    }

    public void setProfileVisible(boolean profileVisible) {
        this.profileVisible = profileVisible;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public boolean isCellphoneVisible() {
        return cellphoneVisible;
    }

    public void setCellphoneVisible(boolean cellphoneVisible) {
        this.cellphoneVisible = cellphoneVisible;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
