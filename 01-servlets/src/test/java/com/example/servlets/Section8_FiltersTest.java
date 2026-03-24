package com.example.servlets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты фильтров (Filters).
 */
@SpringBootTest
class Section8_FiltersTest {

  @Test
  void shouldHaveLoggingFilter() {
    Class<?> clazz = null;
    try {
      clazz = Class.forName("com.example.servlets.filter.LoggingFilter");
    } catch (ClassNotFoundException e) {
      throw new AssertionError("LoggingFilter class not found", e);
    }
    assertThat(clazz).isNotNull();
    assertThat(clazz.getSimpleName()).isEqualTo("LoggingFilter");
  }

  @Test
  void shouldHaveTimingFilter() {
    Class<?> clazz = null;
    try {
      clazz = Class.forName("com.example.servlets.filter.TimingFilter");
    } catch (ClassNotFoundException e) {
      throw new AssertionError("TimingFilter class not found", e);
    }
    assertThat(clazz).isNotNull();
    assertThat(clazz.getSimpleName()).isEqualTo("TimingFilter");
  }
}
