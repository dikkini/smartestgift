package com.paymybill.exception;

import org.springframework.security.core.AuthenticationException;

public class UserBlockedException extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    private int code;

    public UserBlockedException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
