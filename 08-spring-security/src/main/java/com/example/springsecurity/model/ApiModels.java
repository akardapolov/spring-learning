package com.example.springsecurity.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public final class ApiModels {

  private ApiModels() {
  }

  public record ApiResponse<T>(boolean success, T data) {
    public static <T> ApiResponse<T> ok(T data) {
      return new ApiResponse<>(true, data);
    }
  }

  public record ApiErrorResponse(boolean success, String errorCode, String message) {
    public static ApiErrorResponse of(String errorCode, String message) {
      return new ApiErrorResponse(false, errorCode, message);
    }
  }

  public record AuthResponse(String token, String type, UserDto user) {
  }

  public record LoginRequest(String username, String password) {
  }

  public record UserDto(Long id, String username, String email, String fullName, Boolean enabled,
                        Set<String> roles, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
  }

  public record UserCreateRequest(String username, String password, String email, String firstName,
                                String lastName) {
  }

  public record UserUpdateRequest(String email, String firstName, String lastName) {
  }

  public record UserPasswordUpdateRequest(String oldPassword, String newPassword) {
  }

  public record RoleDto(Long id, String name, String description, Set<String> permissions) {
  }

  public record RoleCreateRequest(String name, String description, Set<String> permissions) {
  }

  public record PermissionDto(Long id, String name, String description) {
  }

  public record PermissionCreateRequest(String name, String description) {
  }

  // Demo response models
  public record SecurityConceptItem(String concept, String description, String example) {
  }

  public record SecurityConceptsResult(List<SecurityConceptItem> concepts) {
  }

  public record FilterChainItem(String order, String filter, String description) {
  }

  public record FilterChainResult(List<FilterChainItem> filters) {
  }

  public record ArchitectureOverview(String authentication, String authorization,
                                  String sessionManagement, String csrfProtection) {
  }

  public record UserDetailsResult(String username, String email, List<String> roles,
                                  List<String> permissions, boolean enabled) {
  }

  public record PasswordEncoderInfo(String algorithm, String encodingStrength,
                                  String exampleEncoded) {
  }

  public record SecurityEventItem(String eventType, String timestamp, String username,
                                String details) {
  }

  public record SecurityEventsResult(List<SecurityEventItem> events) {
  }

  public record GlossaryItem(String term, String definition) {
  }

  public record GlossaryResult(List<GlossaryItem> items) {
  }

  public record EndpointItem(String method, String path, String description, String authRequired) {
  }

  public record EndpointListResult(List<EndpointItem> endpoints) {
  }

  public record AuthVsAuthorizationItem(String aspect, String authentication, String authorization) {
  }

  public record AuthVsAuthorizationResult(List<AuthVsAuthorizationItem> items) {
  }

  public record MethodSecurityItem(String annotation, String description, String example) {
  }

  public record MethodSecurityResult(List<MethodSecurityItem> items) {
  }

  public record SecurityConfigItem(String key, String value, String description) {
  }

  public record SecurityConfigResult(List<SecurityConfigItem> items) {
  }
}
