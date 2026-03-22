package com.example.springsecurity.config;

import com.example.springsecurity.entity.Permission;
import com.example.springsecurity.entity.Role;
import com.example.springsecurity.entity.User;
import com.example.springsecurity.repository.PermissionRepository;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DemoDataInitializer implements ApplicationRunner {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  private final PasswordEncoder passwordEncoder;

  public DemoDataInitializer(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PermissionRepository permissionRepository,
                           PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.permissionRepository = permissionRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public void run(ApplicationArguments args) {
    if (userRepository.count() > 0) {
      return;
    }

    // Create permissions
    Permission readPerm = permissionRepository.save(
        new Permission("USER_READ", "Read user information"));
    Permission writePerm = permissionRepository.save(
        new Permission("USER_WRITE", "Write user information"));
    Permission deletePerm = permissionRepository.save(
        new Permission("USER_DELETE", "Delete user information"));
    Permission roleReadPerm = permissionRepository.save(
        new Permission("ROLE_READ", "Read role information"));
    Permission roleWritePerm = permissionRepository.save(
        new Permission("ROLE_WRITE", "Write role information"));
    Permission permReadPerm = permissionRepository.save(
        new Permission("PERMISSION_READ", "Read permission information"));
    Permission permWritePerm = permissionRepository.save(
        new Permission("PERMISSION_WRITE", "Write permission information"));

    // Create USER role
    Role userRole = new Role("USER", "Regular user with basic access");
    userRole.addPermission(readPerm);
    roleRepository.save(userRole);

    // Create MODERATOR role
    Role moderatorRole = new Role("MODERATOR", "Moderator with extended access");
    moderatorRole.addPermission(readPerm);
    moderatorRole.addPermission(writePerm);
    moderatorRole.addPermission(roleReadPerm);
    roleRepository.save(moderatorRole);

    // Create ADMIN role with all permissions
    Role adminRole = new Role("ADMIN", "Administrator with full access");
    adminRole.addPermission(readPerm);
    adminRole.addPermission(writePerm);
    adminRole.addPermission(deletePerm);
    adminRole.addPermission(roleReadPerm);
    adminRole.addPermission(roleWritePerm);
    adminRole.addPermission(permReadPerm);
    adminRole.addPermission(permWritePerm);
    roleRepository.save(adminRole);

    // Create admin user
    User admin = new User("admin", passwordEncoder.encode("admin123"), "admin@example.com");
    admin.setFirstName("System");
    admin.setLastName("Administrator");
    admin.addRole(adminRole);
    userRepository.save(admin);

    // Create regular user
    User user = new User("user", passwordEncoder.encode("user123"), "user@example.com");
    user.setFirstName("John");
    user.setLastName("Doe");
    user.addRole(userRole);
    userRepository.save(user);

    // Create moderator user
    User moderator = new User("moderator", passwordEncoder.encode("moderator123"), "moderator@example.com");
    moderator.setFirstName("Jane");
    moderator.setLastName("Smith");
    moderator.addRole(moderatorRole);
    userRepository.save(moderator);

    // Create disabled user
    User disabledUser = new User("disabled_user", passwordEncoder.encode("disabled123"), "disabled@example.com");
    disabledUser.setFirstName("Disabled");
    disabledUser.setLastName("Account");
    disabledUser.setEnabled(false);
    disabledUser.addRole(userRole);
    userRepository.save(disabledUser);
  }
}
