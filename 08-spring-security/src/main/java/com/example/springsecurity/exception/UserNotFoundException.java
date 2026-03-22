package com.example.springsecurity.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException(Long id) {
    super("User not found: " + id);
  }

  public UserNotFoundException(String username, boolean isUsername) {
    super("User not found: " + username);
  }
}
