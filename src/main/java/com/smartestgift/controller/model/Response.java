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

    private Response(T message){
        this.message = message;
    }

    public static <T> Response<T> createResponse(T message){
        return new Response<T>(message);
    }

    public T getMessage() {
        return message;
    }
}
