package com.example.springdatajpa;

import com.example.springdatajpa.model.ApiModels.QueryMethodsResult;
import com.example.springdatajpa.service.SpringDataDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section2_QueryMethodsTest {

  private final SpringDataDemoService service;

  Section2_QueryMethodsTest(SpringDataDemoService service) {
    this.service = service;
  }

  @Test
  void shouldExecuteQueryMethods() {
    QueryMethodsResult result = service.queryMethodsDemo();

    assertThat(result.deviceCount()).isGreaterThan(0);
    assertThat(result.electronicsCount()).isGreaterThan(0);
    assertThat(result.firstDeviceName()).isNotBlank();
    assertThat(result.expensiveCount()).isGreaterThan(0);
  }
}