package com.example.springrest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class Section2_RequestParametersTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void whenPathVariableProvided_thenReturnPathVariableInResponse() {
    ResponseEntity<com.example.springrest.model.ApiModels.RequestParametersDemo> response =
        restTemplate.getForEntity(
            "/api/rest/parameters/path/123?queryParam=custom",
            com.example.springrest.model.ApiModels.RequestParametersDemo.class
        );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var body = response.getBody();
    assertThat(body).isNotNull();
    assertThat(body.pathVariable()).isEqualTo("123");
    assertThat(body.queryParam()).isEqualTo("custom");
  }

  @Test
  void whenRequestBodyProvided_thenReturnRequestBodyInResponse() {
    Map<String, Object> requestBody = Map.of("name", "Test Product", "price", 100);

    ResponseEntity<com.example.springrest.model.ApiModels.RequestParametersDemo> response =
        restTemplate.postForEntity(
            "/api/rest/parameters/body",
            requestBody,
            com.example.springrest.model.ApiModels.RequestParametersDemo.class
        );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var body = response.getBody();
    assertThat(body).isNotNull();
    assertThat(body.requestBody()).isNotNull();
  }
}
