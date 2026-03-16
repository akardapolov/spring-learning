package com.example.hibernate;

import com.example.hibernate.model.ApiModels.FlushClearResult;
import com.example.hibernate.service.FlushClearDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section9_FlushClearTest {

  private final FlushClearDemoService service;

  Section9_FlushClearTest(FlushClearDemoService service) {
    this.service = service;
  }

  @Test
  void shouldDemonstrateFlushAndClear() {
    FlushClearResult result = service.demonstrate();

    assertThat(result.containsAfterFlush()).isEqualTo(true);
    assertThat(result.containsAfterClear()).isEqualTo(false);
    assertThat(result.sameJavaInstance()).isEqualTo(false);
  }
}