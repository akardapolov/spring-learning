package com.example.springdatajpa;

import com.example.springdatajpa.model.ApiModels.QueryAnnotationResult;
import com.example.springdatajpa.service.SpringDataDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section3_QueryAnnotationTest {

  private final SpringDataDemoService service;

  Section3_QueryAnnotationTest(SpringDataDemoService service) {
    this.service = service;
  }

  @Test
  void shouldExecuteJpqlAndNativeQueries() {
    QueryAnnotationResult result = service.queryAnnotationDemo();

    assertThat(result.jpqlCount()).isGreaterThan(0);
    assertThat(result.nativeCount()).isGreaterThan(0);
    assertThat(result.firstJpqlName()).isNotBlank();
    assertThat(result.firstNativeName()).isNotBlank();
  }
}