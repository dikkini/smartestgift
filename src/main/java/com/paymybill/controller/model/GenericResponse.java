package com.paymybill.controller.model;

import java.io.Serializable;

public class GenericResponse<T> implements Serializable {

    private static final long serialVersionUID = -3583994886157629752L;

    private final boolean success;
    private T data;
    private String message;
    private String developerMessage;

    private GenericResponse(boolean success) {
        this.success = success;
    }

    private GenericResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    private GenericResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    private GenericResponse(boolean success, String message, String developerMessage) {
        this.success = success;
        this.message = message;
        this.developerMessage = developerMessage;
    }

    private GenericResponse(boolean success, T data, String message, String developerMessage) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.developerMessage = developerMessage;
    }

    public static <T> GenericResponse<T> createResponse(boolean success) {
        return new GenericResponse<>(success);
    }

    public static <T> GenericResponse<T> createResponse(boolean success, String messsage) {
        return new GenericResponse<T>(success, messsage);
    }

    public static <T> GenericResponse<T> createResponse(boolean success, T data) {
        return new GenericResponse<T>(success, data);
    }

    public static <T> GenericResponse<T> createResponse(boolean success, T data, String message, String developerMessage) {
        return new GenericResponse<>(success, data, message, developerMessage);
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }
}
