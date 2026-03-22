package com.example.springsecurity;

import com.example.springsecurity.model.ApiModels.SecurityConfigItem;
import com.example.springsecurity.model.ApiModels.SecurityConfigResult;
import com.example.springsecurity.service.SpringSecurityDemoService;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Section5_SecurityConfigTest {

  private final SpringSecurityDemoService service = new SpringSecurityDemoService(null, null, null);

  @Test
  void shouldReturnSecurityConfigOptions() {
    SecurityConfigResult result = service.securityConfigDemo();

    assertThat(result.items()).isNotEmpty();
  }

  @Test
  void shouldContainPasswordEncoderConfig() {
    SecurityConfigResult result = service.securityConfigDemo();

    SecurityConfigItem passwordEncoder = result.items().stream()
        .filter(i -> i.key().equals("passwordEncoder"))
        .findFirst()
        .orElseThrow();

    assertThat(passwordEncoder.value()).contains("BCryptPasswordEncoder");
    assertThat(passwordEncoder.description()).containsIgnoringCase("encrypts");
  }

  @Test
  void shouldContainCsrfDisableConfig() {
    SecurityConfigResult result = service.securityConfigDemo();

    SecurityConfigItem csrf = result.items().stream()
        .filter(i -> i.key().equals("csrf().disable()"))
        .findFirst()
        .orElseThrow();

    assertThat(csrf.description()).contains("CSRF");
  }

  @Test
  void shouldContainAuthorizeHttpRequestsConfig() {
    SecurityConfigResult result = service.securityConfigDemo();

    SecurityConfigItem authz = result.items().stream()
        .filter(i -> i.key().equals("authorizeHttpRequests()"))
        .findFirst()
        .orElseThrow();

    assertThat(authz.description()).containsIgnoringCase("endpoint");
    assertThat(authz.description()).containsIgnoringCase("authentication");
  }

  @Test
  void shouldContainFormLoginConfig() {
    SecurityConfigResult result = service.securityConfigDemo();

    SecurityConfigItem formLogin = result.items().stream()
        .filter(i -> i.key().equals("formLogin()"))
        .findFirst()
        .orElseThrow();

    assertThat(formLogin.description()).contains("login page");
  }

  @Test
  void shouldContainHttpBasicConfig() {
    SecurityConfigResult result = service.securityConfigDemo();

    SecurityConfigItem httpBasic = result.items().stream()
        .filter(i -> i.key().equals("httpBasic()"))
        .findFirst()
        .orElseThrow();

    assertThat(httpBasic.description()).containsIgnoringCase("Basic");
  }

  @Test
  void shouldContainSessionManagementConfig() {
    SecurityConfigResult result = service.securityConfigDemo();

    SecurityConfigItem sessionMgmt = result.items().stream()
        .filter(i -> i.key().equals("sessionManagement().sessionCreationPolicy(STATELESS)"))
        .findFirst()
        .orElseThrow();

    assertThat(sessionMgmt.description()).contains("stateless");
  }

  @Test
  void shouldContainLogoutConfig() {
    SecurityConfigResult result = service.securityConfigDemo();

    SecurityConfigItem logout = result.items().stream()
        .filter(i -> i.key().equals("logout()"))
        .findFirst()
        .orElseThrow();

    assertThat(logout.description()).containsIgnoringCase("logout");
  }
}
