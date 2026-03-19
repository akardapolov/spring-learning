package com.example.springdatajdbc;

import com.example.springdatajdbc.model.ApiModels.DifferencesResult;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section1_DifferencesTest {

  private final SpringDataJdbcDemoService service;

  Section1_DifferencesTest(SpringDataJdbcDemoService service) {
    this.service = service;
  }

  @Test
  void shouldListDifferencesBetweenJdbcAndJpa() {
    DifferencesResult result = service.differencesDemo();

    assertThat(result.items()).hasSizeGreaterThanOrEqualTo(5);
    assertThat(result.items().stream().anyMatch(i -> i.topic().equals("Lazy Loading"))).isTrue();
    assertThat(result.items().stream().anyMatch(i -> i.topic().equals("Dirty Checking"))).isTrue();
  }
}
