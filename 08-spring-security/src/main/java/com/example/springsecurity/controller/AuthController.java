package com.example.springsecurity.controller;

import com.example.springsecurity.model.ApiModels.ApiResponse;
import com.example.springsecurity.model.ApiModels.AuthResponse;
import com.example.springsecurity.model.ApiModels.LoginRequest;
import com.example.springsecurity.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
    return ApiResponse.ok(userService.authenticate(request.username(), request.password()));
  }
}
