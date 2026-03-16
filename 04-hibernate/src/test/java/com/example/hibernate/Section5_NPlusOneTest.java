package com.example.hibernate;

import com.example.hibernate.model.ApiModels.StatisticsSnapshot;
import com.example.hibernate.service.NPlusOneDemoService;
import com.example.hibernate.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section5_NPlusOneTest {

  private final NPlusOneDemoService service;
  private final StatisticsService statisticsService;

  Section5_NPlusOneTest(NPlusOneDemoService service, StatisticsService statisticsService) {
    this.service = service;
    this.statisticsService = statisticsService;
  }

  @Test
  void joinFetchShouldUseNoMoreQueriesThanLazyScenario() {
    statisticsService.reset();
    service.lazyScenario();
    StatisticsSnapshot lazySnapshot = statisticsService.snapshot();
    long lazyStatements = lazySnapshot.prepareStatementCount();

    statisticsService.reset();
    service.joinFetchScenario();
    StatisticsSnapshot joinFetchSnapshot = statisticsService.snapshot();
    long joinFetchStatements = joinFetchSnapshot.prepareStatementCount();

    assertThat(joinFetchStatements).isLessThanOrEqualTo(lazyStatements);
  }
}