package com.example.springsecurity;

import com.example.springsecurity.model.ApiModels.SecurityConceptItem;
import com.example.springsecurity.model.ApiModels.SecurityConceptsResult;
import com.example.springsecurity.service.SpringSecurityDemoService;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Section1_SecurityConceptsTest {

  private final SpringSecurityDemoService service = new SpringSecurityDemoService(null, null, null);

  @Test
  void shouldReturnSecurityConcepts() {
    SecurityConceptsResult result = service.securityConceptsDemo();

    assertThat(result.concepts()).isNotEmpty();

    List<String> conceptNames = result.concepts().stream()
        .map(SecurityConceptItem::concept)
        .toList();

    assertThat(conceptNames).contains(
        "Authentication",
        "Authorization",
        "Principal",
        "GrantedAuthority",
        "SecurityContext",
        "SecurityFilterChain"
    );
  }

  @Test
  void shouldContainAuthenticationConcept() {
    SecurityConceptsResult result = service.securityConceptsDemo();

    SecurityConceptItem authConcept = result.concepts().stream()
        .filter(c -> c.concept().equals("Authentication"))
        .findFirst()
        .orElseThrow();

    assertThat(authConcept.description())
        .isEqualTo("Process of verifying the identity of a user or system");
    assertThat(authConcept.example()).isNotEmpty();
  }

  @Test
  void shouldContainAuthorizationConcept() {
    SecurityConceptsResult result = service.securityConceptsDemo();

    SecurityConceptItem authzConcept = result.concepts().stream()
        .filter(c -> c.concept().equals("Authorization"))
        .findFirst()
        .orElseThrow();

    assertThat(authzConcept.description())
        .isEqualTo("Process of determining what authenticated users are allowed to do");
    assertThat(authzConcept.example()).isNotEmpty();
  }
}
