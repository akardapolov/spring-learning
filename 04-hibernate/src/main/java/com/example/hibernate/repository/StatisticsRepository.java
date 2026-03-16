package com.example.hibernate.repository;

import com.example.hibernate.model.ApiModels.StatisticsSnapshot;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Repository;

@Repository
public class StatisticsRepository {

  private final EntityManagerFactory entityManagerFactory;

  public StatisticsRepository(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  public void clear() {
    getStatistics().clear();
  }

  public StatisticsSnapshot snapshot() {
    Statistics statistics = getStatistics();
    return new StatisticsSnapshot(
        statistics.getEntityLoadCount(),
        statistics.getEntityFetchCount(),
        statistics.getQueryExecutionCount(),
        statistics.getPrepareStatementCount(),
        statistics.getSecondLevelCacheHitCount(),
        statistics.getSecondLevelCacheMissCount(),
        statistics.getSecondLevelCachePutCount(),
        statistics.getQueryCacheHitCount(),
        statistics.getQueryCacheMissCount(),
        statistics.getQueryCachePutCount(),
        statistics.getFlushCount(),
        statistics.getOptimisticFailureCount()
    );
  }

  private Statistics getStatistics() {
    SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    return sessionFactory.getStatistics();
  }
}