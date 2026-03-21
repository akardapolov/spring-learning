package com.example.springrest;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springrest.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class Section4_ExceptionHandlingTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void whenGetNonExistentProduct_thenReturn404ErrorResponse() {
    ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(
        "/api/rest/products/999",
        ErrorResponse.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    ErrorResponse error = response.getBody();
    assertThat(error).isNotNull();
    assertThat(error.status()).isEqualTo(404);
    assertThat(error.error()).isEqualTo("Not Found");
    assertThat(error.message()).contains("not found");
  }

  @Test
  void whenTriggerNotFound_thenReturn404ErrorResponse() {
    ResponseEntity<String> response = restTemplate.getForEntity(
        "/api/rest/exception/not-found",
        String.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void whenTriggerBadRequest_thenReturn400ErrorResponse() {
    ResponseEntity<String> response = restTemplate.getForEntity(
        "/api/rest/exception/bad-request",
        String.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void whenGetNonExistentCategory_thenReturn404ErrorResponse() {
    ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(
        "/api/rest/categories/999",
        ErrorResponse.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    ErrorResponse error = response.getBody();
    assertThat(error).isNotNull();
    assertThat(error.status()).isEqualTo(404);
  }
}
