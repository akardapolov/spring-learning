package com.example.hibernate;

import com.example.hibernate.model.ApiModels.SpringDataEntityGraphResult;
import com.example.hibernate.model.ApiModels.SpringDataPaginationResult;
import com.example.hibernate.model.ApiModels.SpringDataQueryMethodsResult;
import com.example.hibernate.springdata.SpringDataService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section7_SpringDataTest {

  private final SpringDataService service;

  Section7_SpringDataTest(SpringDataService service) {
    this.service = service;
  }

  @Test
  void shouldExecuteSpringDataMethods() {
    SpringDataQueryMethodsResult result = service.queryMethodsDemo();
    assertThat(result.devicesCount()).isGreaterThanOrEqualTo(0);
    assertThat(result.electronicsProductsCount()).isGreaterThanOrEqualTo(0);
  }

  @Test
  void shouldExecuteEntityGraphAndPagination() {
    SpringDataEntityGraphResult graph = service.queryAndEntityGraphDemo();
    SpringDataPaginationResult page = service.paginationDemo();

    assertThat(graph.detailedCategoryProducts()).isGreaterThan(0);
    assertThat(page.contentSize()).isGreaterThanOrEqualTo(0);
  }
}