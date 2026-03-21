package com.example.springrest;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springrest.dto.ErrorResponse;
import com.example.springrest.dto.ProductCreateRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Section5_ValidationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void whenCreateProductWithBlankName_thenReturn400ValidationError() {
    ProductCreateRequest request = new ProductCreateRequest(
        "",
        "DEVICE",
        BigDecimal.valueOf(100),
        "Acme",
        "SKU-001",
        null
    );

    ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
        "/api/rest/products",
        request,
        ErrorResponse.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    ErrorResponse error = response.getBody();
    assertThat(error).isNotNull();
    assertThat(error.status()).isEqualTo(400);
    assertThat(error.error()).isEqualTo("Validation Failed");
    assertThat(error.validationErrors()).isNotNull();
    assertThat(error.validationErrors()).hasSizeGreaterThan(0);
    assertThat(error.validationErrors().stream()
        .anyMatch(e -> e.field().equals("name") || e.message().toLowerCase().contains("name")))
        .isTrue();
  }

  @Test
  void whenCreateProductWithZeroPrice_thenReturn400ValidationError() {
    ProductCreateRequest request = new ProductCreateRequest(
        "Test Product",
        "DEVICE",
        BigDecimal.ZERO,
        "Acme",
        "SKU-001",
        null
    );

    ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
        "/api/rest/products",
        request,
        ErrorResponse.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    ErrorResponse error = response.getBody();
    assertThat(error).isNotNull();
    assertThat(error.status()).isEqualTo(400);
    assertThat(error.error()).isEqualTo("Validation Failed");
  }

  @Test
  void whenCreateProductWithValidData_thenReturn201Created() {
    ProductCreateRequest request = new ProductCreateRequest(
        "Valid Product",
        "DEVICE",
        BigDecimal.valueOf(100),
        "Acme",
        "SKU-VALID",
        null
    );

    ResponseEntity<com.example.springrest.model.ApiModels.CrudOperationResult> response =
        restTemplate.postForEntity(
            "/api/rest/products",
            request,
            com.example.springrest.model.ApiModels.CrudOperationResult.class
        );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    var result = response.getBody();
    assertThat(result).isNotNull();
    assertThat(result.operation()).isEqualTo("CREATE");
  }

  @Test
  void whenCreateProductWithInvalidPrice_thenReturn400ValidationError() {
    ProductCreateRequest request = new ProductCreateRequest(
        "Test Product",
        "DEVICE",
        null,
        "Acme",
        "SKU-001",
        null
    );

    ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
        "/api/rest/products",
        request,
        ErrorResponse.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    ErrorResponse error = response.getBody();
    assertThat(error).isNotNull();
    assertThat(error.status()).isEqualTo(400);
    assertThat(error.error()).isEqualTo("Validation Failed");
  }
}
