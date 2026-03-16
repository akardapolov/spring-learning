package com.example.hibernate.controller;

import com.example.hibernate.model.ApiModels.ApiErrorResponse;
import com.example.hibernate.exception.ProductNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ProductNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiErrorResponse handleProductNotFoundException(ProductNotFoundException ex) {
    return ApiErrorResponse.of("PRODUCT_NOT_FOUND", ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {
    return ApiErrorResponse.of("BAD_REQUEST", ex.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiErrorResponse handleEntityNotFoundException(EntityNotFoundException ex) {
    return ApiErrorResponse.of("NOT_FOUND", ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiErrorResponse handleException(Exception ex) {
    return ApiErrorResponse.of("INTERNAL_ERROR", ex.getMessage());
  }
}