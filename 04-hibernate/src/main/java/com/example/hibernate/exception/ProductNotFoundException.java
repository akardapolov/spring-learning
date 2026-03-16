package com.example.hibernate.exception;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(Long productId) {
    super("Product not found: " + productId);
  }
}