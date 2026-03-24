package com.example.servlets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты листенеров (Listeners).
 */
@SpringBootTest
class Section9_ListenersTest {

  @Test
  void shouldHaveServletContextListener() {
    Class<?> clazz = null;
    try {
      clazz = Class.forName("com.example.servlets.listener.ApplicationServletContextListener");
    } catch (ClassNotFoundException e) {
      throw new AssertionError("ApplicationServletContextListener class not found", e);
    }
    assertThat(clazz).isNotNull();
    assertThat(clazz.getSimpleName()).isEqualTo("ApplicationServletContextListener");
  }

  @Test
  void shouldHaveHttpSessionListener() {
    Class<?> clazz = null;
    try {
      clazz = Class.forName("com.example.servlets.listener.ApplicationHttpSessionListener");
    } catch (ClassNotFoundException e) {
      throw new AssertionError("ApplicationHttpSessionListener class not found", e);
    }
    assertThat(clazz).isNotNull();
    assertThat(clazz.getSimpleName()).isEqualTo("ApplicationHttpSessionListener");
  }

  @Test
  void shouldHaveServletRequestListener() {
    Class<?> clazz = null;
    try {
      clazz = Class.forName("com.example.servlets.listener.ApplicationServletRequestListener");
    } catch (ClassNotFoundException e) {
      throw new AssertionError("ApplicationServletRequestListener class not found", e);
    }
    assertThat(clazz).isNotNull();
    assertThat(clazz.getSimpleName()).isEqualTo("ApplicationServletRequestListener");
  }
}
