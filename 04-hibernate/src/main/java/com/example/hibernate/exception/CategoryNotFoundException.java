package com.example.hibernate.exception;

public class CategoryNotFoundException extends RuntimeException {

  public CategoryNotFoundException(Long productId) {
    super("Product not found: " + productId);
  }
}