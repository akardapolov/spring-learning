package com.example.springsecurity.controller;

import com.example.springsecurity.model.ApiModels.ApiResponse;
import com.example.springsecurity.model.ApiModels.PermissionCreateRequest;
import com.example.springsecurity.model.ApiModels.PermissionDto;
import com.example.springsecurity.service.PermissionService;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

  private final PermissionService permissionService;

  public PermissionController(PermissionService permissionService) {
    this.permissionService = permissionService;
  }

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ApiResponse<List<PermissionDto>> getAllPermissions() {
    return ApiResponse.ok(permissionService.getAllPermissions());
  }

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ApiResponse<PermissionDto> getPermissionById(@PathVariable Long id) {
    return ApiResponse.ok(permissionService.getPermissionById(id));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<PermissionDto> createPermission(@RequestBody PermissionCreateRequest request) {
    return ApiResponse.ok(permissionService.createPermission(request));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<String> deletePermission(@PathVariable Long id) {
    permissionService.deletePermission(id);
    return ApiResponse.ok("Permission deleted successfully");
  }
}
