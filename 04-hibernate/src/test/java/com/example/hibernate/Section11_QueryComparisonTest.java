package com.example.hibernate;

import com.example.hibernate.model.ApiModels.QueryComparisonResult;
import com.example.hibernate.service.QueryComparisonService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section11_QueryComparisonTest {

  private final QueryComparisonService service;

  Section11_QueryComparisonTest(QueryComparisonService service) {
    this.service = service;
  }

  @Test
  void shouldReturnSameLogicalCountsForDifferentQueryApis() {
    QueryComparisonResult result = service.compare();

    assertThat(result.nativeCount()).isEqualTo(result.jpqlCount());
    assertThat(result.jpqlCount()).isEqualTo(result.criteriaCount());
  }
}