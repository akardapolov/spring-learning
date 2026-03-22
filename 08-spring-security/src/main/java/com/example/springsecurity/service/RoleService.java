package com.example.springsecurity.service;

import com.example.springsecurity.entity.Permission;
import com.example.springsecurity.entity.Role;
import com.example.springsecurity.exception.RoleNotFoundException;
import com.example.springsecurity.model.ApiModels.RoleCreateRequest;
import com.example.springsecurity.model.ApiModels.RoleDto;
import com.example.springsecurity.repository.PermissionRepository;
import com.example.springsecurity.repository.RoleRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
    this.roleRepository = roleRepository;
    this.permissionRepository = permissionRepository;
  }

  public RoleDto getRoleById(Long id) {
    Role role = roleRepository.findById(id)
        .orElseThrow(() -> new RoleNotFoundException(id));
    return toRoleDto(role);
  }

  public RoleDto getRoleByName(String name) {
    Role role = roleRepository.findByName(name)
        .orElseThrow(() -> new RoleNotFoundException(name));
    return toRoleDto(role);
  }

  public java.util.List<RoleDto> getAllRoles() {
    return roleRepository.findAll().stream()
        .map(this::toRoleDto)
        .toList();
  }

  @Transactional
  public RoleDto createRole(RoleCreateRequest request) {
    if (roleRepository.existsByName(request.name())) {
      throw new IllegalArgumentException("Role already exists: " + request.name());
    }

    Role role = new Role(request.name(), request.description());

    if (request.permissions() != null) {
      for (String permName : request.permissions()) {
        Permission permission = permissionRepository.findByName(permName)
            .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + permName));
        role.addPermission(permission);
      }
    }

    Role saved = roleRepository.save(role);
    return toRoleDto(saved);
  }

  @Transactional
  public void deleteRole(Long id) {
    Role role = roleRepository.findById(id)
        .orElseThrow(() -> new RoleNotFoundException(id));

    roleRepository.delete(role);
  }

  @Transactional
  public RoleDto addPermissionToRole(Long roleId, Long permissionId) {
    Role role = roleRepository.findById(roleId)
        .orElseThrow(() -> new RoleNotFoundException(roleId));
    Permission permission = permissionRepository.findById(permissionId)
        .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + permissionId));

    role.addPermission(permission);
    Role saved = roleRepository.save(role);
    return toRoleDto(saved);
  }

  @Transactional
  public RoleDto removePermissionFromRole(Long roleId, Long permissionId) {
    Role role = roleRepository.findById(roleId)
        .orElseThrow(() -> new RoleNotFoundException(roleId));
    Permission permission = permissionRepository.findById(permissionId)
        .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + permissionId));

    role.removePermission(permission);
    Role saved = roleRepository.save(role);
    return toRoleDto(saved);
  }

  private RoleDto toRoleDto(Role role) {
    Set<String> permissionNames = role.getPermissions().stream()
        .map(Permission::getName)
        .collect(Collectors.toSet());

    return new RoleDto(
        role.getId(),
        role.getName(),
        role.getDescription(),
        permissionNames
    );
  }
}
