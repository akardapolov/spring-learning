package com.example.springdatajpa.controller;

import com.example.springdatajpa.model.ApiModels.ApiErrorResponse;
import com.example.springdatajpa.exception.CategoryNotFoundException;
import com.example.springdatajpa.exception.ProductNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ProductNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiErrorResponse handleProductNotFoundException(ProductNotFoundException ex) {
    return ApiErrorResponse.of("PRODUCT_NOT_FOUND", ex.getMessage());
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiErrorResponse handleCategoryNotFoundException(CategoryNotFoundException ex) {
    return ApiErrorResponse.of("CATEGORY_NOT_FOUND", ex.getMessage());
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
    log.error("Unhandled exception", ex);
    return ApiErrorResponse.of("INTERNAL_ERROR", "An unexpected error occurred");
  }
}