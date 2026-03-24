package com.example.servlets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты Response API Servlet.
 */
@SpringBootTest
class Section3_ResponseApiTest {

  @Test
  void shouldHaveResponseApiServlet() {
    Class<?> clazz = null;
    try {
      clazz = Class.forName("com.example.servlets.servlet.ResponseApiServlet");
    } catch (ClassNotFoundException e) {
      throw new AssertionError("ResponseApiServlet class not found", e);
    }
    assertThat(clazz).isNotNull();
    assertThat(clazz.getSimpleName()).isEqualTo("ResponseApiServlet");
  }
}
