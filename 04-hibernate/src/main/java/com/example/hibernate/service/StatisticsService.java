package com.example.hibernate.service;

import com.example.hibernate.model.ApiModels.StatisticsSnapshot;
import com.example.hibernate.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

  private final StatisticsRepository repository;

  public StatisticsService(StatisticsRepository repository) {
    this.repository = repository;
  }

  public void reset() {
    repository.clear();
  }

  public StatisticsSnapshot snapshot() {
    return repository.snapshot();
  }
}