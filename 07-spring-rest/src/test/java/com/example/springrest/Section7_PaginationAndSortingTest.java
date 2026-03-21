package com.example.springrest;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springrest.dto.ProductDto;
import com.example.springrest.model.ApiModels.PaginationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class Section7_PaginationAndSortingTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void whenGetPaginatedProducts_thenReturnPaginatedResult() {
    ResponseEntity<PaginationResult> response = restTemplate.getForEntity(
        "/api/rest/products/paginated?page=0&size=3",
        PaginationResult.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    PaginationResult result = response.getBody();
    assertThat(result).isNotNull();
    assertThat(result.page()).isEqualTo(0);
    assertThat(result.size()).isEqualTo(3);
    assertThat(result.totalElements()).isEqualTo(6); // Total from DemoDataInitializer
    assertThat(result.totalPages()).isEqualTo(2);
    assertThat(result.content()).hasSize(3);
  }

  @Test
  void whenGetSecondPage_thenReturnSecondPage() {
    ResponseEntity<PaginationResult> response = restTemplate.getForEntity(
        "/api/rest/products/paginated?page=1&size=3",
        PaginationResult.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    PaginationResult result = response.getBody();
    assertThat(result).isNotNull();
    assertThat(result.page()).isEqualTo(1);
    assertThat(result.content()).hasSize(3);
  }

  @Test
  void whenSortDescending_thenReturnProductsInDescendingOrder() {
    ResponseEntity<PaginationResult> response = restTemplate.getForEntity(
        "/api/rest/products/paginated?page=0&size=10&sortBy=price&sortDir=desc",
        PaginationResult.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    PaginationResult result = response.getBody();
    assertThat(result).isNotNull();
    assertThat(result.content()).hasSize(6);

    // Verify descending order by price
    for (int i = 0; i < result.content().size() - 1; i++) {
      ProductDto current = result.content().get(i);
      ProductDto next = result.content().get(i + 1);
      assertThat(current.price()).isGreaterThanOrEqualTo(next.price());
    }
  }

  @Test
  void whenSearchByType_thenReturnFilteredResults() {
    ResponseEntity<PaginationResult> response = restTemplate.getForEntity(
        "/api/rest/products/search?type=DEVICE&page=0&size=10",
        PaginationResult.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    PaginationResult result = response.getBody();
    assertThat(result).isNotNull();
    assertThat(result.content()).hasSize(2); // 2 devices: Phone and Laptop
  }
}
