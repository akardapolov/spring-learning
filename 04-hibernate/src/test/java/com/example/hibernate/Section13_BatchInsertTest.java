package com.example.hibernate;

import com.example.hibernate.model.ApiModels.BatchInsertResult;
import com.example.hibernate.service.BatchInsertService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section13_BatchInsertTest {

  private final BatchInsertService service;

  Section13_BatchInsertTest(BatchInsertService service) {
    this.service = service;
  }

  @Test
  void shouldInsertBatch() {
    BatchInsertResult result = service.insert(15);
    assertThat(result.inserted()).isEqualTo(15);
  }
}