package com.example.springsecurity.controller;

import com.example.springsecurity.model.ApiModels.ApiResponse;
import com.example.springsecurity.model.ApiModels.RoleCreateRequest;
import com.example.springsecurity.model.ApiModels.RoleDto;
import com.example.springsecurity.service.RoleService;
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
@RequestMapping("/api/roles")
public class RoleController {

  private final RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ApiResponse<List<RoleDto>> getAllRoles() {
    return ApiResponse.ok(roleService.getAllRoles());
  }

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ApiResponse<RoleDto> getRoleById(@PathVariable Long id) {
    return ApiResponse.ok(roleService.getRoleById(id));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<RoleDto> createRole(@RequestBody RoleCreateRequest request) {
    return ApiResponse.ok(roleService.createRole(request));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<String> deleteRole(@PathVariable Long id) {
    roleService.deleteRole(id);
    return ApiResponse.ok("Role deleted successfully");
  }

  @PostMapping("/{roleId}/permissions/{permId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<RoleDto> addPermissionToRole(@PathVariable Long roleId,
                                                 @PathVariable Long permId) {
    return ApiResponse.ok(roleService.addPermissionToRole(roleId, permId));
  }

  @DeleteMapping("/{roleId}/permissions/{permId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ApiResponse<RoleDto> removePermissionFromRole(@PathVariable Long roleId,
                                                    @PathVariable Long permId) {
    return ApiResponse.ok(roleService.removePermissionFromRole(roleId, permId));
  }
}
