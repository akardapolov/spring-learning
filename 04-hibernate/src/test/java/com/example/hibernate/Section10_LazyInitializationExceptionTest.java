package com.example.hibernate;

import com.example.hibernate.model.ApiModels.LazyExceptionResult;
import com.example.hibernate.service.LazyExceptionDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section10_LazyInitializationExceptionTest {

  private final LazyExceptionDemoService service;

  Section10_LazyInitializationExceptionTest(LazyExceptionDemoService service) {
    this.service = service;
  }

  @Test
  void shouldDemonstrateLazyInitializationException() {
    LazyExceptionResult result = service.demonstrate(1L);
    assertThat(result.lazyInitializationException()).isEqualTo(true);
  }
}