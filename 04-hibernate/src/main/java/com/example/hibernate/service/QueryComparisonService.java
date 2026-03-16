package com.example.hibernate.service;

import com.example.hibernate.model.ApiModels.QueryComparisonResult;
import com.example.hibernate.repository.QueryComparisonRepository;
import org.springframework.stereotype.Service;

@Service
public class QueryComparisonService {

  private final QueryComparisonRepository repository;

  public QueryComparisonService(QueryComparisonRepository repository) {
    this.repository = repository;
  }

  public QueryComparisonResult compare() {
    int nativeCount = repository.nativeQueryByType("DEVICE").size();
    int jpqlCount = repository.jpqlByType("DEVICE").size();
    int criteriaCount = repository.criteriaByType("DEVICE").size();

    return new QueryComparisonResult(nativeCount, jpqlCount, criteriaCount);
  }
}