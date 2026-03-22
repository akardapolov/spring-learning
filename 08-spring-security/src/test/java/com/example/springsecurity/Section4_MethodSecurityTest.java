package com.example.springsecurity;

import com.example.springsecurity.model.ApiModels.MethodSecurityItem;
import com.example.springsecurity.model.ApiModels.MethodSecurityResult;
import com.example.springsecurity.service.SpringSecurityDemoService;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Section4_MethodSecurityTest {

  private final SpringSecurityDemoService service = new SpringSecurityDemoService(null, null, null);

  @Test
  void shouldReturnMethodSecurityAnnotations() {
    MethodSecurityResult result = service.methodSecurityDemo();

    assertThat(result.items()).isNotEmpty();
  }

  @Test
  void shouldContainSecuredAnnotation() {
    MethodSecurityResult result = service.methodSecurityDemo();

    MethodSecurityItem secured = result.items().stream()
        .filter(i -> i.annotation().equals("@Secured"))
        .findFirst()
        .orElseThrow();

    assertThat(secured.description()).contains("role-based");
    assertThat(secured.example()).contains("ROLE_ADMIN");
  }

  @Test
  void shouldContainPreAuthorizeAnnotation() {
    MethodSecurityResult result = service.methodSecurityDemo();

    MethodSecurityItem preAuthorize = result.items().stream()
        .filter(i -> i.annotation().equals("@PreAuthorize"))
        .findFirst()
        .orElseThrow();

    assertThat(preAuthorize.description()).contains("SpEL");
    assertThat(preAuthorize.description()).contains("before method execution");
  }

  @Test
  void shouldContainHasRoleExpression() {
    MethodSecurityResult result = service.methodSecurityDemo();

    MethodSecurityItem hasRole = result.items().stream()
        .filter(i -> i.annotation().equals("hasRole('ROLE')"))
        .findFirst()
        .orElseThrow();

    assertThat(hasRole.description()).contains("ROLE_ prefix");
  }

  @Test
  void shouldContainIsAuthenticatedExpression() {
    MethodSecurityResult result = service.methodSecurityDemo();

    MethodSecurityItem isAuthenticated = result.items().stream()
        .filter(i -> i.annotation().equals("isAuthenticated()"))
        .findFirst()
        .orElseThrow();

    assertThat(isAuthenticated.description()).contains("not anonymous");
  }

  @Test
  void shouldContainPermitAllExpression() {
    MethodSecurityResult result = service.methodSecurityDemo();

    MethodSecurityItem permitAll = result.items().stream()
        .filter(i -> i.annotation().equals("permitAll()"))
        .findFirst()
        .orElseThrow();

    assertThat(permitAll.description()).contains("grants access");
    assertThat(permitAll.description()).contains("regardless of authentication");
  }

  @Test
  void shouldContainPostAuthorizeAnnotation() {
    MethodSecurityResult result = service.methodSecurityDemo();

    MethodSecurityItem postAuthorize = result.items().stream()
        .filter(i -> i.annotation().equals("@PostAuthorize"))
        .findFirst()
        .orElseThrow();

    assertThat(postAuthorize.description()).contains("after method execution");
    assertThat(postAuthorize.description()).contains("return values");
  }

  @Test
  void shouldContainFilterAnnotations() {
    MethodSecurityResult result = service.methodSecurityDemo();

    boolean hasPreFilter = result.items().stream()
        .anyMatch(i -> i.annotation().equals("@PreFilter"));
    boolean hasPostFilter = result.items().stream()
        .anyMatch(i -> i.annotation().equals("@PostFilter"));

    assertThat(hasPreFilter).isTrue();
    assertThat(hasPostFilter).isTrue();
  }
}
