package com.example.servlets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты Request API Servlet.
 */
@SpringBootTest
class Section2_RequestApiTest {

  @Test
  void shouldHaveRequestApiServlet() {
    Class<?> clazz = null;
    try {
      clazz = Class.forName("com.example.servlets.servlet.RequestApiServlet");
    } catch (ClassNotFoundException e) {
      throw new AssertionError("RequestApiServlet class not found", e);
    }
    assertThat(clazz).isNotNull();
    assertThat(clazz.getSimpleName()).isEqualTo("RequestApiServlet");
  }
}
