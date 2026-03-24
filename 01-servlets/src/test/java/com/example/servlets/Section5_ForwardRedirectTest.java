package com.example.servlets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты Forward vs Redirect Servlet.
 */
@SpringBootTest
class Section5_ForwardRedirectTest {

  @Test
  void shouldHaveForwardRedirectServlet() {
    Class<?> clazz = null;
    try {
      clazz = Class.forName("com.example.servlets.servlet.ForwardRedirectServlet");
    } catch (ClassNotFoundException e) {
      throw new AssertionError("ForwardRedirectServlet class not found", e);
    }
    assertThat(clazz).isNotNull();
    assertThat(clazz.getSimpleName()).isEqualTo("ForwardRedirectServlet");
  }
}
