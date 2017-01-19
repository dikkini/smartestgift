package com.paymybill.exception;

import org.springframework.security.core.AuthenticationException;

public class EmailExistException extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    private int code;

    public EmailExistException(String message) {
        super(message);
    }


    public EmailExistException(String message, int code) {
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
