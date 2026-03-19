package com.example.springdatajdbc;

import com.example.springdatajdbc.config.CategoryLifecycleListener;
import com.example.springdatajdbc.config.LifecycleEventsCollector;
import com.example.springdatajdbc.model.ApiModels.LifecycleEventResult;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import({
    SpringDataJdbcDemoService.class,
    LifecycleEventsCollector.class,
    CategoryLifecycleListener.class
})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section5_LifecycleEventsTest {

  private final SpringDataJdbcDemoService service;

  Section5_LifecycleEventsTest(SpringDataJdbcDemoService service) {
    this.service = service;
  }

  @Test
  void shouldCaptureLifecycleEventsInExactOrderForSaveThenLoad() {
    LifecycleEventResult result = service.lifecycleDemo();

    assertThat(result.events()).containsExactly(
        "BeforeConvert: Lifecycle Demo",
        "BeforeSave: Lifecycle Demo",
        "AfterSave: Lifecycle Demo",
        "AfterConvert: Lifecycle Demo"
    );
  }
}