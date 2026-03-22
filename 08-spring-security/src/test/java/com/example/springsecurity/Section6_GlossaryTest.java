package com.example.springsecurity;

import com.example.springsecurity.model.ApiModels.GlossaryItem;
import com.example.springsecurity.model.ApiModels.GlossaryResult;
import com.example.springsecurity.service.SpringSecurityDemoService;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Section6_GlossaryTest {

  private final SpringSecurityDemoService service = new SpringSecurityDemoService(null, null, null);

  @Test
  void shouldReturnGlossary() {
    GlossaryResult result = service.glossaryDemo();

    assertThat(result.items()).isNotEmpty();
  }

  @Test
  void shouldContainAuthenticationTerm() {
    GlossaryResult result = service.glossaryDemo();

    GlossaryItem authentication = result.items().stream()
        .filter(i -> i.term().equals("Authentication"))
        .findFirst()
        .orElseThrow();

    assertThat(authentication.definition()).contains("verify");
    assertThat(authentication.definition()).contains("identity");
  }

  @Test
  void shouldContainAuthorizationTerm() {
    GlossaryResult result = service.glossaryDemo();

    GlossaryItem authorization = result.items().stream()
        .filter(i -> i.term().equals("Authorization"))
        .findFirst()
        .orElseThrow();

    assertThat(authorization.definition()).contains("access rights");
  }

  @Test
  void shouldContainPrincipalTerm() {
    GlossaryResult result = service.glossaryDemo();

    boolean hasPrincipal = result.items().stream()
        .anyMatch(i -> i.term().equals("Principal"));

    assertThat(hasPrincipal).isTrue();
  }

  @Test
  void shouldContainSecurityFilterChainTerm() {
    GlossaryResult result = service.glossaryDemo();

    GlossaryItem filterChain = result.items().stream()
        .filter(i -> i.term().equals("SecurityFilterChain"))
        .findFirst()
        .orElseThrow();

    assertThat(filterChain.definition()).contains("filters");
    assertThat(filterChain.definition()).contains("HTTP request");
  }

  @Test
  void shouldContainUserDetailsTerm() {
    GlossaryResult result = service.glossaryDemo();

    GlossaryItem userDetails = result.items().stream()
        .filter(i -> i.term().equals("UserDetails"))
        .findFirst()
        .orElseThrow();

    assertThat(userDetails.definition()).contains("user information");
  }

  @Test
  void shouldContainPasswordEncoderTerm() {
    GlossaryResult result = service.glossaryDemo();

    GlossaryItem passwordEncoder = result.items().stream()
        .filter(i -> i.term().equals("PasswordEncoder"))
        .findFirst()
        .orElseThrow();

    assertThat(passwordEncoder.definition()).contains("BCrypt");
  }

  @Test
  void shouldContainCsrfTerm() {
    GlossaryResult result = service.glossaryDemo();

    GlossaryItem csrf = result.items().stream()
        .filter(i -> i.term().equals("CSRF"))
        .findFirst()
        .orElseThrow();

    assertThat(csrf.definition()).contains("Cross-Site Request Forgery");
  }

  @Test
  void shouldContainRbacTerm() {
    GlossaryResult result = service.glossaryDemo();

    GlossaryItem rbac = result.items().stream()
        .filter(i -> i.term().equals("RBAC"))
        .findFirst()
        .orElseThrow();

    assertThat(rbac.definition()).contains("Role-Based Access Control");
  }
}
