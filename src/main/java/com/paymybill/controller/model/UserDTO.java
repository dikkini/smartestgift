package com.paymybill.controller.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    @NotNull
    @Size(min = 3, max = 30)
    private String username;

    @NotNull
    @Size(min = 3, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 6, max = 30)
    private String password;

    @NotNull
    @Size(min = 6, max = 30)
    private String repeatPassword;

    @NotNull
    @Size(min = 3, max = 30)
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
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
