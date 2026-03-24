package com.example.servlets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты Error Handling Servlet.
 */
@SpringBootTest
class Section6_ErrorHandlingTest {

  @Test
  void shouldHaveErrorHandlingServlet() {
    Class<?> clazz = null;
    try {
      clazz = Class.forName("com.example.servlets.servlet.ErrorHandlingServlet");
    } catch (ClassNotFoundException e) {
      throw new AssertionError("ErrorHandlingServlet class not found", e);
    }
    assertThat(clazz).isNotNull();
    assertThat(clazz.getSimpleName()).isEqualTo("ErrorHandlingServlet");
  }
}
