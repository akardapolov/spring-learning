package com.example.springrest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Section3_ResponseHandlingTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void whenResponseOk_thenReturn200Status() {
    ResponseEntity<String> response = restTemplate.getForEntity("/api/rest/response/ok", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void whenResponseCreated_thenReturn201Status() {
    ResponseEntity<com.example.springrest.model.ApiModels.ResponseHandlingDemo> response =
        restTemplate.getForEntity(
            "/api/rest/response/created",
            com.example.springrest.model.ApiModels.ResponseHandlingDemo.class
        );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    var body = response.getBody();
    assertThat(body).isNotNull();
    assertThat(body.statusCode()).isEqualTo(201);
  }

  @Test
  void whenResponseNoContent_thenReturn204Status() {
    ResponseEntity<com.example.springrest.model.ApiModels.ResponseHandlingDemo> response =
        restTemplate.getForEntity(
            "/api/rest/response/no_content",
            com.example.springrest.model.ApiModels.ResponseHandlingDemo.class
        );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void whenRequestCustomHeaders_thenReturnHeaders() {
    ResponseEntity<String> response = restTemplate.getForEntity(
        "/api/rest/response/custom-headers",
        String.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getHeaders()).containsKey("X-Custom-Header");
    assertThat(response.getHeaders()).containsKey("X-Application-Name");
  }
}
