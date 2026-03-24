package com.example.springsecurity.service;

import com.example.springsecurity.entity.User;
import com.example.springsecurity.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("IDENTIFICATION: searching user '{}'", username);

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> {
          log.warn("IDENTIFICATION FAILED: user '{}' not found", username);
          return new UsernameNotFoundException("User not found: " + username);
        });

    List<GrantedAuthority> authorities = new ArrayList<>();

    user.getRoles().forEach(role -> {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
      role.getPermissions().forEach(permission ->
                                        authorities.add(new SimpleGrantedAuthority(permission.getName()))
      );
    });

    log.info("IDENTIFICATION SUCCESS: username='{}', enabled={}, roles={}",
             user.getUsername(),
             user.getEnabled(),
             user.getRoles().stream().map(r -> r.getName()).toList()
    );

    return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPassword())
        .disabled(!Boolean.TRUE.equals(user.getEnabled()))
        .accountExpired(!Boolean.TRUE.equals(user.getAccountNonExpired()))
        .accountLocked(!Boolean.TRUE.equals(user.getAccountNonLocked()))
        .credentialsExpired(!Boolean.TRUE.equals(user.getCredentialsNonExpired()))
        .authorities(authorities)
        .build();
  }
}