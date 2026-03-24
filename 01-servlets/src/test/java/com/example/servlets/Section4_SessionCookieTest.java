package com.example.servlets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты Session и Cookie API Servlet.
 */
@SpringBootTest
class Section4_SessionCookieTest {

  @Test
  void shouldHaveSessionCookieServlet() {
    Class<?> clazz = null;
    try {
      clazz = Class.forName("com.example.servlets.servlet.SessionCookieServlet");
    } catch (ClassNotFoundException e) {
      throw new AssertionError("SessionCookieServlet class not found", e);
    }
    assertThat(clazz).isNotNull();
    assertThat(clazz.getSimpleName()).isEqualTo("SessionCookieServlet");
  }
}
