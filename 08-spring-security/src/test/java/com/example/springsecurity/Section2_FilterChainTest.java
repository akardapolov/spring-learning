package com.example.springsecurity;

import com.example.springsecurity.model.ApiModels.FilterChainItem;
import com.example.springsecurity.model.ApiModels.FilterChainResult;
import com.example.springsecurity.service.SpringSecurityDemoService;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Section2_FilterChainTest {

  private final SpringSecurityDemoService service = new SpringSecurityDemoService(null, null, null);

  @Test
  void shouldReturnFilterChain() {
    FilterChainResult result = service.filterChainDemo();

    assertThat(result.filters()).hasSize(15);
  }

  @Test
  void shouldContainCsrfFilter() {
    FilterChainResult result = service.filterChainDemo();

    FilterChainItem csrfFilter = result.filters().stream()
        .filter(f -> f.filter().contains("Csrf"))
        .findFirst()
        .orElseThrow();

    assertThat(csrfFilter.filter()).isEqualTo("CsrfFilter");
    assertThat(csrfFilter.description())
        .contains("Cross-Site Request Forgery");
  }

  @Test
  void shouldContainUsernamePasswordAuthenticationFilter() {
    FilterChainResult result = service.filterChainDemo();

    FilterChainItem filter = result.filters().stream()
        .filter(f -> f.filter().contains("UsernamePassword"))
        .findFirst()
        .orElseThrow();

    assertThat(filter.filter()).isEqualTo("UsernamePasswordAuthenticationFilter");
    assertThat(filter.description()).contains("form-based login");
  }

  @Test
  void shouldContainExceptionTranslationFilter() {
    FilterChainResult result = service.filterChainDemo();

    FilterChainItem filter = result.filters().stream()
        .filter(f -> f.filter().equals("ExceptionTranslationFilter"))
        .findFirst()
        .orElseThrow();

    assertThat(filter.description())
        .contains("Spring Security exceptions");
    assertThat(filter.description()).contains("HTTP responses");
  }

  @Test
  void filtersShouldBeInOrder() {
    FilterChainResult result = service.filterChainDemo();

    List<String> orders = result.filters().stream()
        .map(FilterChainItem::order)
        .toList();

    assertThat(orders).containsExactly(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "11", "12", "13", "14", "15"
    );
  }
}
