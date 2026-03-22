package com.example.springsecurity;

import com.example.springsecurity.model.ApiModels.ArchitectureOverview;
import com.example.springsecurity.service.SpringSecurityDemoService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Section8_ArchitectureTest {

  private final SpringSecurityDemoService service = new SpringSecurityDemoService(null, null, null);

  @Test
  void shouldReturnArchitectureOverview() {
    ArchitectureOverview result = service.architectureDemo();

    assertThat(result).isNotNull();
    assertThat(result.authentication()).isNotEmpty();
    assertThat(result.authorization()).isNotEmpty();
    assertThat(result.sessionManagement()).isNotEmpty();
    assertThat(result.csrfProtection()).isNotEmpty();
  }

  @Test
  void shouldContainAuthenticationInfo() {
    ArchitectureOverview result = service.architectureDemo();

    assertThat(result.authentication())
        .contains("Form-based login")
        .contains("JWT")
        .contains("OAuth2");
  }

  @Test
  void shouldContainAuthorizationInfo() {
    ArchitectureOverview result = service.architectureDemo();

    assertThat(result.authorization())
        .contains("@PreAuthorize")
        .contains("@Secured")
        .contains("RBAC");
  }

  @Test
  void shouldContainSessionManagementInfo() {
    ArchitectureOverview result = service.architectureDemo();

    assertThat(result.sessionManagement())
        .contains("Session-based")
        .contains("JWT");
  }

  @Test
  void shouldContainCsrfProtectionInfo() {
    ArchitectureOverview result = service.architectureDemo();

    assertThat(result.csrfProtection())
        .contains("CSRF")
        .contains("stateless");
  }
}
