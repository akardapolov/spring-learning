package com.example.springboot.exception;

/**
 * Base application exception.
 */
public class AppException extends RuntimeException {

    private final String code;

    public AppException(String message) {
        super(message);
        this.code = "APP_ERROR";
    }

    public AppException(String code, String message) {
        super(message);
        this.code = code;
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
        this.code = "APP_ERROR";
    }

    public String getCode() {
        return code;
    }
}
