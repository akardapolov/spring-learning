package com.example.springsecurity.service;

import com.example.springsecurity.config.SecurityEventsCollector;
import com.example.springsecurity.entity.Role;
import com.example.springsecurity.entity.User;
import com.example.springsecurity.exception.RoleNotFoundException;
import com.example.springsecurity.exception.UserAlreadyExistsException;
import com.example.springsecurity.exception.UserNotFoundException;
import com.example.springsecurity.model.ApiModels.AuthResponse;
import com.example.springsecurity.model.ApiModels.UserCreateRequest;
import com.example.springsecurity.model.ApiModels.UserDto;
import com.example.springsecurity.model.ApiModels.UserPasswordUpdateRequest;
import com.example.springsecurity.model.ApiModels.UserUpdateRequest;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final SecurityEventsCollector eventsCollector;

  public UserService(UserRepository userRepository,
                    RoleRepository roleRepository,
                    PasswordEncoder passwordEncoder,
                    SecurityEventsCollector eventsCollector) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.eventsCollector = eventsCollector;
  }

  public AuthResponse authenticate(String username, String password) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      eventsCollector.recordEvent("AUTHENTICATION_FAILED", username, "Invalid password");
      throw new BadCredentialsException("Invalid credentials");
    }

    user.setLastLoginAt(LocalDateTime.now());
    userRepository.save(user);

    eventsCollector.recordEvent("AUTHENTICATION_SUCCESS", username, "User logged in successfully");

    return new AuthResponse("mock-jwt-token-" + System.currentTimeMillis(), "Bearer", toUserDto(user));
  }

  public UserDto getUserById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));
    return toUserDto(user);
  }

  public UserDto getUserByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
    return toUserDto(user);
  }

  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream()
        .map(this::toUserDto)
        .toList();
  }

  @Transactional
  public UserDto createUser(UserCreateRequest request) {
    if (userRepository.existsByUsername(request.username())) {
      throw new UserAlreadyExistsException("Username already exists: " + request.username());
    }

    if (userRepository.existsByEmail(request.email())) {
      throw new UserAlreadyExistsException("Email already exists: " + request.email());
    }

    User user = new User(
        request.username(),
        passwordEncoder.encode(request.password()),
        request.email()
    );
    user.setFirstName(request.firstName());
    user.setLastName(request.lastName());
    user.setEnabled(true);

    // Assign default USER role
    Role userRole = roleRepository.findByName("USER")
        .orElseThrow(() -> new RoleNotFoundException("Default USER role not found", true));
    user.addRole(userRole);

    User saved = userRepository.save(user);
    eventsCollector.recordEvent("USER_CREATED", saved.getUsername(), "New user account created");

    return toUserDto(saved);
  }

  @Transactional
  public UserDto updateUser(Long id, UserUpdateRequest request) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));

    user.setEmail(request.email());
    user.setFirstName(request.firstName());
    user.setLastName(request.lastName());

    User saved = userRepository.save(user);
    return toUserDto(saved);
  }

  @Transactional
  public void updatePassword(Long id, UserPasswordUpdateRequest request) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));

    if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
      throw new BadCredentialsException("Current password is incorrect");
    }

    user.setPassword(passwordEncoder.encode(request.newPassword()));
    userRepository.save(user);

    eventsCollector.recordEvent("PASSWORD_CHANGED", user.getUsername(), "User password updated");
  }

  @Transactional
  public void deleteUser(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));

    String username = user.getUsername();
    userRepository.deleteById(id);

    eventsCollector.recordEvent("USER_DELETED", username, "User account deleted");
  }

  @Transactional
  public UserDto assignRole(Long userId, Long roleId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    Role role = roleRepository.findById(roleId)
        .orElseThrow(() -> new RoleNotFoundException(roleId));

    user.addRole(role);
    User saved = userRepository.save(user);

    eventsCollector.recordEvent("ROLE_ASSIGNED", user.getUsername(),
        "Role " + role.getName() + " assigned to user");

    return toUserDto(saved);
  }

  @Transactional
  public UserDto removeRole(Long userId, Long roleId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    Role role = roleRepository.findById(roleId)
        .orElseThrow(() -> new RoleNotFoundException(roleId));

    user.removeRole(role);
    User saved = userRepository.save(user);

    eventsCollector.recordEvent("ROLE_REMOVED", user.getUsername(),
        "Role " + role.getName() + " removed from user");

    return toUserDto(saved);
  }

  @Transactional
  public void enableUser(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));

    user.setEnabled(true);
    userRepository.save(user);

    eventsCollector.recordEvent("USER_ENABLED", user.getUsername(), "User account enabled");
  }

  @Transactional
  public void disableUser(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));

    user.setEnabled(false);
    userRepository.save(user);

    eventsCollector.recordEvent("USER_DISABLED", user.getUsername(), "User account disabled");
  }

  private UserDto toUserDto(User user) {
    Set<String> roleNames = user.getRoles().stream()
        .map(Role::getName)
        .collect(Collectors.toSet());

    return new UserDto(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getFullName(),
        user.getEnabled(),
        roleNames,
        user.getCreatedAt(),
        user.getLastLoginAt()
    );
  }
}
