package com.example.servlets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты базового HttpServlet.
 * Демонстрация обработки HTTP методов: GET, POST, PUT, DELETE.
 * Примечание: Raw @WebServlet сервлеты не тестируются через MockMvc,
 * так как MockMvc предназначен только для Spring MVC (@RestController).
 * Сервлеты проверяются при компиляции и интеграционном тестировании.
 */
@SpringBootTest
class Section1_BasicHttpServletTest {

  @Test
  void shouldHaveBasicHttpServletClass() {
    Class<?> clazz = null;
    try {
      clazz = Class.forName("com.example.servlets.servlet.BasicHttpServlet");
    } catch (ClassNotFoundException e) {
      throw new AssertionError("BasicHttpServlet class not found", e);
    }
    assertThat(clazz).isNotNull();
    assertThat(clazz.getSimpleName()).isEqualTo("BasicHttpServlet");
  }

  @Test
  void shouldHaveProductModel() {
    Class<?> clazz = null;
    try {
      clazz = Class.forName("com.example.servlets.model.Product");
    } catch (ClassNotFoundException e) {
      throw new AssertionError("Product class not found", e);
    }
    assertThat(clazz).isNotNull();
    assertThat(clazz.getSimpleName()).isEqualTo("Product");
  }
}
