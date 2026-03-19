package com.example.springdatajdbc;

import com.example.springdatajdbc.model.ApiModels.LifecycleEventResult;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section5_LifecycleEventsTest {

  private final SpringDataJdbcDemoService service;

  Section5_LifecycleEventsTest(SpringDataJdbcDemoService service) {
    this.service = service;
  }

  @Test
  void shouldCaptureAllLifecycleEvents() {
    LifecycleEventResult result = service.lifecycleDemo();

    assertThat(result.events()).isNotEmpty();
    assertThat(result.events().stream().anyMatch(e -> e.startsWith("BeforeConvert"))).isTrue();
    assertThat(result.events().stream().anyMatch(e -> e.startsWith("BeforeSave"))).isTrue();
    assertThat(result.events().stream().anyMatch(e -> e.startsWith("AfterSave"))).isTrue();
    assertThat(result.events().stream().anyMatch(e -> e.startsWith("AfterConvert"))).isTrue();
  }
}
