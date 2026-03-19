package com.example.springdatajdbc;

import com.example.springdatajdbc.model.ApiModels.DddResult;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section2_DddAggregatesTest {

  private final SpringDataJdbcDemoService service;

  Section2_DddAggregatesTest(SpringDataJdbcDemoService service) {
    this.service = service;
  }

  @Test
  void shouldDescribeAllAggregates() {
    DddResult result = service.dddDemo();

    assertThat(result.aggregates()).hasSize(3);
    assertThat(result.aggregates().stream().anyMatch(a -> a.aggregateRoot().equals("Category"))).isTrue();
    assertThat(result.aggregates().stream().anyMatch(a -> a.aggregateRoot().equals("Customer"))).isTrue();
    assertThat(result.aggregates().stream().anyMatch(a -> a.aggregateRoot().equals("Tag"))).isTrue();
  }
}
