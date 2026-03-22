package com.example.springsecurity.service;

import com.example.springsecurity.entity.Permission;
import com.example.springsecurity.model.ApiModels.PermissionCreateRequest;
import com.example.springsecurity.model.ApiModels.PermissionDto;
import com.example.springsecurity.repository.PermissionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionService {

  private final PermissionRepository permissionRepository;

  public PermissionService(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  public PermissionDto getPermissionById(Long id) {
    Permission permission = permissionRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + id));
    return toPermissionDto(permission);
  }

  public List<PermissionDto> getAllPermissions() {
    return permissionRepository.findAll().stream()
        .map(this::toPermissionDto)
        .toList();
  }

  @Transactional
  public PermissionDto createPermission(PermissionCreateRequest request) {
    if (permissionRepository.existsByName(request.name())) {
      throw new IllegalArgumentException("Permission already exists: " + request.name());
    }

    Permission permission = new Permission(request.name(), request.description());
    Permission saved = permissionRepository.save(permission);
    return toPermissionDto(saved);
  }

  @Transactional
  public void deletePermission(Long id) {
    Permission permission = permissionRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + id));

    permissionRepository.delete(permission);
  }

  private PermissionDto toPermissionDto(Permission permission) {
    return new PermissionDto(
        permission.getId(),
        permission.getName(),
        permission.getDescription()
    );
  }
}
