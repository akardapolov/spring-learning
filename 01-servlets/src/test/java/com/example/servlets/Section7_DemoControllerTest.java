package com.example.servlets;

import com.example.servlets.controller.DemoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section7_DemoControllerTest {

  private final DemoController controller;
  private final MockMvc mockMvc;

  Section7_DemoControllerTest(DemoController controller, MockMvc mockMvc) {
    this.controller = controller;
    this.mockMvc = mockMvc;
  }

  @Test
  void shouldReturnIndex() throws Exception {
    var response = controller.index();
    assertThat(response.status()).isEqualTo(200);
    assertThat(response.message()).isEqualTo("Servlets Demo");
  }

  @Test
  void shouldReturnIndexViaMockMvc() throws Exception {
    mockMvc.perform(get("/api/demo"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnServletApiInfo() {
    var response = controller.servletApi();
    assertThat(response.status()).isEqualTo(200);
  }

  @Test
  void shouldReturnServletApiInfoViaMockMvc() throws Exception {
    mockMvc.perform(get("/api/demo/servlet-api"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnRequestApiInfo() {
    var response = controller.requestApi(false);
    assertThat(response.status()).isEqualTo(200);
  }

  @Test
  void shouldReturnRequestApiInfoViaMockMvc() throws Exception {
    mockMvc.perform(get("/api/demo/request-api"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnResponseApiInfo() {
    var response = controller.responseApi();
    assertThat(response.status()).isEqualTo(200);
  }

  @Test
  void shouldReturnResponseApiInfoViaMockMvc() throws Exception {
    mockMvc.perform(get("/api/demo/response-api"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnSessionApiInfo() {
    var response = controller.sessionApi();
    assertThat(response.status()).isEqualTo(200);
  }

  @Test
  void shouldReturnSessionApiInfoViaMockMvc() throws Exception {
    mockMvc.perform(get("/api/demo/session-api"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnFilterApiInfo() {
    var response = controller.filterApi();
    assertThat(response.status()).isEqualTo(200);
  }

  @Test
  void shouldReturnFilterApiInfoViaMockMvc() throws Exception {
    mockMvc.perform(get("/api/demo/filter-api"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnListenerApiInfo() {
    var response = controller.listenerApi();
    assertThat(response.status()).isEqualTo(200);
  }

  @Test
  void shouldReturnListenerApiInfoViaMockMvc() throws Exception {
    mockMvc.perform(get("/api/demo/listener-api"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnForwardRedirectInfo() {
    var response = controller.forwardRedirect();
    assertThat(response.status()).isEqualTo(200);
  }

  @Test
  void shouldReturnForwardRedirectInfoViaMockMvc() throws Exception {
    mockMvc.perform(get("/api/demo/forward-redirect"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnErrorHandlingInfo() {
    var response = controller.errorHandling();
    assertThat(response.status()).isEqualTo(200);
  }

  @Test
  void shouldReturnErrorHandlingInfoViaMockMvc() throws Exception {
    mockMvc.perform(get("/api/demo/error-handling"))
        .andExpect(status().isOk());
  }
}
