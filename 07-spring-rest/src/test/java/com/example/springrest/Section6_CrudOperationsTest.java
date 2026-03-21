package com.example.springrest;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springrest.dto.ProductCreateRequest;
import com.example.springrest.dto.ProductDto;
import com.example.springrest.dto.ProductUpdateRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class Section6_CrudOperationsTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @Order(1)
  void whenGetAllProducts_thenReturnListOfProducts() {
    ResponseEntity<String> response = restTemplate.getForEntity(
        "/api/rest/products",
        String.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    String body = response.getBody();
    assertThat(body).isNotNull();
    assertThat(body).contains("\"id\"");
  }

  @Test
  void whenGetProductById_thenReturnProduct() {
    ResponseEntity<ProductDto> response = restTemplate.getForEntity(
        "/api/rest/products/1",
        ProductDto.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    ProductDto product = response.getBody();
    assertThat(product).isNotNull();
    assertThat(product.id()).isEqualTo(1L);
    assertThat(product.name()).isEqualTo("Phone");
  }

  @Test
  @DirtiesContext
  void whenCreateProduct_thenReturn201Created() {
    ProductCreateRequest request = new ProductCreateRequest(
        "New Product",
        "DEVICE",
        BigDecimal.valueOf(299.99),
        "TechCo",
        "NEW-001",
        null
    );

    ResponseEntity<com.example.springrest.model.ApiModels.CrudOperationResult> response =
        restTemplate.postForEntity(
            "/api/rest/products",
            request,
            com.example.springrest.model.ApiModels.CrudOperationResult.class
        );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().operation()).isEqualTo("CREATE");
  }

  @Test
  @DirtiesContext
  void whenUpdateProduct_thenReturnUpdatedProduct() {
    ProductUpdateRequest request = new ProductUpdateRequest(
        "Updated Phone",
        "DEVICE",
        BigDecimal.valueOf(899.99),
        null,
        null,
        null
    );

    ResponseEntity<com.example.springrest.model.ApiModels.CrudOperationResult> response =
        restTemplate.exchange(
            "/api/rest/products/1",
            HttpMethod.PUT,
            new HttpEntity<>(request),
            com.example.springrest.model.ApiModels.CrudOperationResult.class
        );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().operation()).isEqualTo("UPDATE");
  }

  @Test
  @DirtiesContext
  void whenPatchProduct_thenReturnUpdatedProduct() {
    ProductUpdateRequest request = new ProductUpdateRequest(
        null,
        null,
        BigDecimal.valueOf(799.99),
        null,
        null,
        null
    );

    ResponseEntity<com.example.springrest.model.ApiModels.CrudOperationResult> response =
        restTemplate.exchange(
            "/api/rest/products/1",
            HttpMethod.PATCH,
            new HttpEntity<>(request),
            com.example.springrest.model.ApiModels.CrudOperationResult.class
        );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().operation()).isEqualTo("UPDATE");
  }

  @Test
  @DirtiesContext
  void whenDeleteProduct_thenReturn204NoContent() {
    // First create a product to delete
    ProductCreateRequest createRequest = new ProductCreateRequest(
        "To Delete",
        "DEVICE",
        BigDecimal.valueOf(99.99),
        "DeleteCo",
        "DEL-001",
        null
    );

    var createResponse = restTemplate.postForEntity(
        "/api/rest/products",
        createRequest,
        com.example.springrest.model.ApiModels.CrudOperationResult.class
    );

    Long productId = createResponse.getBody().product().id();

    // Delete the product
    ResponseEntity<Void> deleteResponse = restTemplate.exchange(
        "/api/rest/products/" + productId,
        HttpMethod.DELETE,
        null,
        Void.class
    );

    assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }
}
