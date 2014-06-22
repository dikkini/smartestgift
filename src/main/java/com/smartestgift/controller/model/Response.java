package com.smartestgift.controller.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikkini on 26.02.14.
 * Email: dikkini@gmail.com
 */
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -3583994886157629752L;

    private final T message;

    private boolean success;

    private int httpStatusCode;

    private Response(T message){
        this.message = message;
    }

    public static <T> Response<T> createResponse(T message){
        return new Response<T>(message);
    }

    public T getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}
