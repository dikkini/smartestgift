package com.smartestgift.exception;

/**
 * Created by dikkini on 20/06/14.
 */
public class WrongGiftDateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;

    public WrongGiftDateException(String message, int code) {
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
