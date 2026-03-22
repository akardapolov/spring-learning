package com.example.springsecurity;

import com.example.springsecurity.model.ApiModels.EndpointItem;
import com.example.springsecurity.model.ApiModels.EndpointListResult;
import com.example.springsecurity.service.SpringSecurityDemoService;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Section7_EndpointsTest {

  private final SpringSecurityDemoService service = new SpringSecurityDemoService(null, null, null);

  @Test
  void shouldReturnEndpointsList() {
    EndpointListResult result = service.endpointsDemo();

    assertThat(result.endpoints()).isNotEmpty();
  }

  @Test
  void shouldContainPublicEndpoints() {
    EndpointListResult result = service.endpointsDemo();

    List<EndpointItem> publicEndpoints = result.endpoints().stream()
        .filter(e -> e.authRequired().equals("No"))
        .toList();

    assertThat(publicEndpoints).isNotEmpty();

    List<String> publicPaths = publicEndpoints.stream()
        .map(EndpointItem::path)
        .toList();

    assertThat(publicPaths).contains(
        "/api/security/concepts",
        "/api/security/filter-chain",
        "/api/auth/login",
        "/api/security/glossary"
    );
  }

  @Test
  void shouldContainProtectedEndpoints() {
    EndpointListResult result = service.endpointsDemo();

    List<EndpointItem> protectedEndpoints = result.endpoints().stream()
        .filter(e -> e.authRequired().equals("Yes"))
        .toList();

    assertThat(protectedEndpoints).isNotEmpty();

    List<String> protectedPaths = protectedEndpoints.stream()
        .map(EndpointItem::path)
        .toList();

    assertThat(protectedPaths).contains(
        "/api/users",
        "/api/users/{id}",
        "/api/roles",
        "/api/permissions"
    );
  }

  @Test
  void shouldContainAuthEndpoint() {
    EndpointListResult result = service.endpointsDemo();

    EndpointItem loginEndpoint = result.endpoints().stream()
        .filter(e -> e.path().equals("/api/auth/login"))
        .findFirst()
        .orElseThrow();

    assertThat(loginEndpoint.method()).isEqualTo("POST");
    assertThat(loginEndpoint.authRequired()).isEqualTo("No");
  }

  @Test
  void shouldContainUserEndpoints() {
    EndpointListResult result = service.endpointsDemo();

    boolean hasUserList = result.endpoints().stream()
        .anyMatch(e -> e.path().equals("/api/users"));
    boolean hasUserGetById = result.endpoints().stream()
        .anyMatch(e -> e.path().equals("/api/users/{id}"));
    boolean hasUserCreate = result.endpoints().stream()
        .anyMatch(e -> e.path().equals("/api/users") && e.method().equals("POST"));
    boolean hasUserDelete = result.endpoints().stream()
        .anyMatch(e -> e.path().equals("/api/users/{id}") && e.method().equals("DELETE"));

    assertThat(hasUserList).isTrue();
    assertThat(hasUserGetById).isTrue();
    assertThat(hasUserCreate).isTrue();
    assertThat(hasUserDelete).isTrue();
  }

  @Test
  void shouldContainRoleEndpoints() {
    EndpointListResult result = service.endpointsDemo();

    boolean hasRoleList = result.endpoints().stream()
        .anyMatch(e -> e.path().equals("/api/roles"));
    boolean hasRoleCreate = result.endpoints().stream()
        .anyMatch(e -> e.path().equals("/api/roles") && e.method().equals("POST"));

    assertThat(hasRoleList).isTrue();
    assertThat(hasRoleCreate).isTrue();
  }

  @Test
  void shouldContainPermissionEndpoints() {
    EndpointListResult result = service.endpointsDemo();

    boolean hasPermList = result.endpoints().stream()
        .anyMatch(e -> e.path().equals("/api/permissions"));
    boolean hasPermCreate = result.endpoints().stream()
        .anyMatch(e -> e.path().equals("/api/permissions") && e.method().equals("POST"));

    assertThat(hasPermList).isTrue();
    assertThat(hasPermCreate).isTrue();
  }

  @Test
  void shouldContainDemoEndpoints() {
    EndpointListResult result = service.endpointsDemo();

    List<String> demoPaths = result.endpoints().stream()
        .filter(e -> e.path().startsWith("/api/security/"))
        .map(EndpointItem::path)
        .toList();

    assertThat(demoPaths).contains(
        "/api/security/concepts",
        "/api/security/filter-chain",
        "/api/security/architecture",
        "/api/security/auth-vs-authorization",
        "/api/security/method-security",
        "/api/security/password-encoder",
        "/api/security/config",
        "/api/security/events"
    );
  }
}
