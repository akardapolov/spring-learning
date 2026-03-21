package com.example.springrest.controller;

import com.example.springrest.dto.ErrorResponse;
import com.example.springrest.dto.ValidationError;
import com.example.springrest.exception.CategoryNotFoundException;
import com.example.springrest.exception.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ProductNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleProductNotFoundException(
      ProductNotFoundException ex, HttpServletRequest request) {

    return new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        "Not Found",
        ex.getMessage(),
        LocalDateTime.now(),
        request.getRequestURI(),
        null
    );
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleCategoryNotFoundException(
      CategoryNotFoundException ex, HttpServletRequest request) {

    return new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        "Not Found",
        ex.getMessage(),
        LocalDateTime.now(),
        request.getRequestURI(),
        null
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex, HttpServletRequest request) {

    List<ValidationError> validationErrors = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> new ValidationError(
            error.getField(),
            error.getRejectedValue() != null ? error.getRejectedValue().toString() : "null",
            error.getDefaultMessage()
        ))
        .collect(Collectors.toList());

    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Validation Failed",
        "Request validation failed",
        LocalDateTime.now(),
        request.getRequestURI(),
        validationErrors
    );

    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleConstraintViolationException(
      ConstraintViolationException ex, HttpServletRequest request) {

    List<ValidationError> validationErrors = ex.getConstraintViolations().stream()
        .map(violation -> {
          String field = violation.getPropertyPath().toString();
          if (field.contains(".")) {
            field = field.substring(field.lastIndexOf('.') + 1);
          }
          return new ValidationError(
              field,
              violation.getInvalidValue() != null ? violation.getInvalidValue().toString() : "null",
              violation.getMessage()
          );
        })
        .collect(Collectors.toList());

    return new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Validation Failed",
        "Constraint violation occurred",
        LocalDateTime.now(),
        request.getRequestURI(),
        validationErrors
    );
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleIllegalArgumentException(
      IllegalArgumentException ex, HttpServletRequest request) {

    return new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        ex.getMessage(),
        LocalDateTime.now(),
        request.getRequestURI(),
        null
    );
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleException(Exception ex, HttpServletRequest request) {
    log.error("Unhandled exception", ex);

    return new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        "An unexpected error occurred",
        LocalDateTime.now(),
        request.getRequestURI(),
        null
    );
  }
}
