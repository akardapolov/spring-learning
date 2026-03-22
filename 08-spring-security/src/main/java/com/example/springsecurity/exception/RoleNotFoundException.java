package com.example.springsecurity.exception;

public class RoleNotFoundException extends RuntimeException {

  public RoleNotFoundException(String message) {
    super(message);
  }

  public RoleNotFoundException(Long id) {
    super("Role not found: " + id);
  }

  public RoleNotFoundException(String name, boolean isName) {
    super("Role not found: " + name);
  }
}
