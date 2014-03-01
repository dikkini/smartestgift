package com.smartestgift.enums.messages;

/**
 * Created by dikkini on 01.03.14.
 * Email: dikkini@gmail.com
 */
public enum SuccessesEnum {
    login_success("login_success"),
    signup_success("signup_success"),
    logout_success("logout_success"),
    user_add_gift("user_add_gift");

    protected String code;

    SuccessesEnum(String codee) {}

    public String getCode() {
        return code;
    }
}
