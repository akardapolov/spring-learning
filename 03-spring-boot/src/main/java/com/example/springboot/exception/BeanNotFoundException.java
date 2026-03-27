package com.example.springboot.exception;

public class BeanNotFoundException extends AppException {

    public BeanNotFoundException(String beanName) {
        super("BEAN_NOT_FOUND", "Bean '" + beanName + "' not found");
    }
}
