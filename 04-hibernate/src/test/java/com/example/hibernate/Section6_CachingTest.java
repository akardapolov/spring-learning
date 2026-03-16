package com.example.hibernate;

import com.example.hibernate.model.ApiModels.CachingResult;
import com.example.hibernate.model.ApiModels.StatisticsSnapshot;
import com.example.hibernate.service.CachingDemoService;
import com.example.hibernate.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Section6_CachingTest {

  private final CachingDemoService cachingDemoService;
  private final StatisticsService statisticsService;

  Section6_CachingTest(CachingDemoService cachingDemoService, StatisticsService statisticsService) {
    this.cachingDemoService = cachingDemoService;
    this.statisticsService = statisticsService;
  }

  @Test
  void shouldUseFirstLevelCacheWithinSameTransaction() {
    CachingResult result = cachingDemoService.firstLevelCache(1L);

    assertThat(result.sameInstanceWithinTransaction()).isTrue();
    assertThat(result.productName()).isNotBlank();
  }

  @Test
  void shouldHitQueryCacheOnRepeatedQuery() {
    statisticsService.reset();

    cachingDemoService.queryCache("DEVICE");
    StatisticsSnapshot afterFirstCall = statisticsService.snapshot();

    cachingDemoService.queryCache("DEVICE");
    StatisticsSnapshot afterSecondCall = statisticsService.snapshot();

    assertThat(afterFirstCall.queryCachePutCount()).isGreaterThanOrEqualTo(1);
    assertThat(afterSecondCall.queryCacheHitCount())
        .isGreaterThan(afterFirstCall.queryCacheHitCount());
  }
}