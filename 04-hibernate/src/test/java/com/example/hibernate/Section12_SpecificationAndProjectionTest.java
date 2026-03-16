package com.example.hibernate;

import com.example.hibernate.model.ApiModels.ProjectionDemoResult;
import com.example.hibernate.model.ApiModels.SpecificationDemoResult;
import com.example.hibernate.service.AdvancedSpringDataService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section12_SpecificationAndProjectionTest {

  private final AdvancedSpringDataService service;

  Section12_SpecificationAndProjectionTest(AdvancedSpringDataService service) {
    this.service = service;
  }

  @Test
  void shouldUseSpecification() {
    SpecificationDemoResult result = service.specificationDemo();
    assertThat(result.matchedCount()).isGreaterThanOrEqualTo(0);
  }

  @Test
  void shouldUseProjection() {
    ProjectionDemoResult result = service.projectionDemo();
    assertThat(result.projectionCount()).isGreaterThanOrEqualTo(0);
  }
}