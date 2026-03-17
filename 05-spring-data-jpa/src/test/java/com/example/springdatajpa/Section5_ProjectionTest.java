package com.example.springdatajpa;

import com.example.springdatajpa.model.ApiModels.ProjectionDemoResult;
import com.example.springdatajpa.service.SpringDataDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section5_ProjectionTest {

  private final SpringDataDemoService service;

  Section5_ProjectionTest(SpringDataDemoService service) {
    this.service = service;
  }

  @Test
  void shouldUseProjection() {
    ProjectionDemoResult result = service.projectionDemo();

    assertThat(result.count()).isGreaterThan(0);
    assertThat(result.firstName()).isNotBlank();
    assertThat(result.items()).isNotEmpty();
  }
}