package com.example.springdatajpa;

import com.example.springdatajpa.model.ApiModels.PageResponse;
import com.example.springdatajpa.model.ApiModels.SliceResponse;
import com.example.springdatajpa.service.SpringDataDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section4_PaginationTest {

  private final SpringDataDemoService service;

  Section4_PaginationTest(SpringDataDemoService service) {
    this.service = service;
  }

  @Test
  void shouldReturnPage() {
    PageResponse result = service.pageDemo(0, 3);

    assertThat(result.pageNumber()).isEqualTo(0);
    assertThat(result.pageSize()).isEqualTo(3);
    assertThat(result.totalElements()).isGreaterThan(0);
    assertThat(result.contentSize()).isLessThanOrEqualTo(3);
    assertThat(result.content()).isNotEmpty();
  }

  @Test
  void shouldReturnSlice() {
    SliceResponse result = service.sliceDemo(0, 1);

    assertThat(result.pageNumber()).isEqualTo(0);
    assertThat(result.pageSize()).isEqualTo(1);
    assertThat(result.contentSize()).isLessThanOrEqualTo(1);
    assertThat(result.content()).isNotEmpty();
  }
}