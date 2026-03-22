package com.example.springsecurity.controller;

import com.example.springsecurity.model.ApiModels.ApiResponse;
import com.example.springsecurity.model.ApiModels.UserCreateRequest;
import com.example.springsecurity.model.ApiModels.UserDto;
import com.example.springsecurity.model.ApiModels.UserPasswordUpdateRequest;
import com.example.springsecurity.model.ApiModels.UserUpdateRequest;
import com.example.springsecurity.service.UserService;
import java.util.List;
import java.util.Set;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<List<UserDto>> getAllUsers() {
    return ApiResponse.ok(userService.getAllUsers());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
  public ApiResponse<UserDto> getUserById(@PathVariable Long id) {
    return ApiResponse.ok(userService.getUserById(id));
  }

  @GetMapping("/me")
  public ApiResponse<UserDto> getCurrentUser() {
    // In a real app, this would get the authenticated user from SecurityContext
    // For demo, returning a sample user
    return ApiResponse.ok(new UserDto(1L, "demo_user", "demo@example.com", "Demo User",
        true, Set.of("USER"), null, null));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<UserDto> createUser(@RequestBody UserCreateRequest request) {
    return ApiResponse.ok(userService.createUser(request));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
  public ApiResponse<UserDto> updateUser(@PathVariable Long id,
                                       @RequestBody UserUpdateRequest request) {
    return ApiResponse.ok(userService.updateUser(id, request));
  }

  @PutMapping("/{id}/password")
  @PreAuthorize("isAuthenticated() and (#id == authentication.principal.id or hasRole('ADMIN'))")
  public ApiResponse<String> updatePassword(@PathVariable Long id,
                                          @RequestBody UserPasswordUpdateRequest request) {
    userService.updatePassword(id, request);
    return ApiResponse.ok("Password updated successfully");
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<String> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ApiResponse.ok("User deleted successfully");
  }

  @PostMapping("/{id}/roles/{roleId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<UserDto> assignRole(@PathVariable Long id, @PathVariable Long roleId) {
    return ApiResponse.ok(userService.assignRole(id, roleId));
  }

  @DeleteMapping("/{id}/roles/{roleId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<UserDto> removeRole(@PathVariable Long id, @PathVariable Long roleId) {
    return ApiResponse.ok(userService.removeRole(id, roleId));
  }

  @PutMapping("/{id}/enable")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<String> enableUser(@PathVariable Long id) {
    userService.enableUser(id);
    return ApiResponse.ok("User enabled successfully");
  }

  @PutMapping("/{id}/disable")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<String> disableUser(@PathVariable Long id) {
    userService.disableUser(id);
    return ApiResponse.ok("User disabled successfully");
  }
}
