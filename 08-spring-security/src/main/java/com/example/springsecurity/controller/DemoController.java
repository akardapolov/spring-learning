package com.example.springsecurity.controller;

import com.example.springsecurity.model.ApiModels.*;
import com.example.springsecurity.service.SpringSecurityDemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security")
public class DemoController {

  private final SpringSecurityDemoService demoService;

  public DemoController(SpringSecurityDemoService demoService) {
    this.demoService = demoService;
  }

  @GetMapping("/concepts")
  public ApiResponse<SecurityConceptsResult> concepts() {
    return ApiResponse.ok(demoService.securityConceptsDemo());
  }

  @GetMapping("/filter-chain")
  public ApiResponse<FilterChainResult> filterChain() {
    return ApiResponse.ok(demoService.filterChainDemo());
  }

  @GetMapping("/architecture")
  public ApiResponse<ArchitectureOverview> architecture() {
    return ApiResponse.ok(demoService.architectureDemo());
  }

  @GetMapping("/auth-vs-authorization")
  public ApiResponse<AuthVsAuthorizationResult> authVsAuthorization() {
    return ApiResponse.ok(demoService.authVsAuthorizationDemo());
  }

  @GetMapping("/method-security")
  public ApiResponse<MethodSecurityResult> methodSecurity() {
    return ApiResponse.ok(demoService.methodSecurityDemo());
  }

  @GetMapping("/password-encoder")
  public ApiResponse<PasswordEncoderInfo> passwordEncoder() {
    return ApiResponse.ok(demoService.passwordEncoderDemo());
  }

  @GetMapping("/config")
  public ApiResponse<SecurityConfigResult> config() {
    return ApiResponse.ok(demoService.securityConfigDemo());
  }

  @GetMapping("/user-details/{username}")
  public ApiResponse<UserDetailsResult> userDetails(@PathVariable String username) {
    return ApiResponse.ok(demoService.userDetailsDemo(username));
  }

  @GetMapping("/events")
  public ApiResponse<SecurityEventsResult> events() {
    return ApiResponse.ok(demoService.securityEventsDemo());
  }

  @GetMapping("/glossary")
  public ApiResponse<GlossaryResult> glossary() {
    return ApiResponse.ok(demoService.glossaryDemo());
  }

  @GetMapping("/endpoints")
  public ApiResponse<EndpointListResult> endpoints() {
    return ApiResponse.ok(demoService.endpointsDemo());
  }
}
