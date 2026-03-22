package com.example.springsecurity;

import com.example.springsecurity.model.ApiModels.AuthVsAuthorizationItem;
import com.example.springsecurity.model.ApiModels.AuthVsAuthorizationResult;
import com.example.springsecurity.service.SpringSecurityDemoService;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Section3_AuthVsAuthorizationTest {

  private final SpringSecurityDemoService service = new SpringSecurityDemoService(null, null, null);

  @Test
  void shouldReturnAuthVsAuthorizationComparison() {
    AuthVsAuthorizationResult result = service.authVsAuthorizationDemo();

    assertThat(result.items()).isNotEmpty();
  }

  @Test
  void shouldContainPurposeComparison() {
    AuthVsAuthorizationResult result = service.authVsAuthorizationDemo();

    AuthVsAuthorizationItem purposeItem = result.items().stream()
        .filter(i -> i.aspect().equals("Purpose"))
        .findFirst()
        .orElseThrow();

    assertThat(purposeItem.authentication()).contains("Who are you");
    assertThat(purposeItem.authorization()).contains("What can you do");
  }

  @Test
  void shouldContainFailureBehaviorComparison() {
    AuthVsAuthorizationResult result = service.authVsAuthorizationDemo();

    AuthVsAuthorizationItem failureItem = result.items().stream()
        .filter(i -> i.aspect().equals("Failure behavior"))
        .findFirst()
        .orElseThrow();

    assertThat(failureItem.authentication()).contains("401");
    assertThat(failureItem.authorization()).contains("403");
  }

  @Test
  void shouldContainWhenItHappensComparison() {
    AuthVsAuthorizationResult result = service.authVsAuthorizationDemo();

    AuthVsAuthorizationItem timingItem = result.items().stream()
        .filter(i -> i.aspect().equals("When it happens"))
        .findFirst()
        .orElseThrow();

    assertThat(timingItem.authentication()).contains("First");
    assertThat(timingItem.authorization()).contains("After authentication");
  }

  @Test
  void shouldContainCommonMechanismComparison() {
    AuthVsAuthorizationResult result = service.authVsAuthorizationDemo();

    AuthVsAuthorizationItem mechanismItem = result.items().stream()
        .filter(i -> i.aspect().equals("Common mechanism"))
        .findFirst()
        .orElseThrow();

    assertThat(mechanismItem.authentication()).containsAnyOf("Username/password", "JWT", "OAuth2");
    assertThat(mechanismItem.authorization()).containsAnyOf("Roles", "permissions", "@PreAuthorize");
  }
}
