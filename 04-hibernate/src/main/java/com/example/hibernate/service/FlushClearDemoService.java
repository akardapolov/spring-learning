package com.example.hibernate.service;

import com.example.hibernate.model.ApiModels.FlushClearResult;
import com.example.hibernate.repository.FlushClearRepository;
import org.springframework.stereotype.Service;

@Service
public class FlushClearDemoService {

  private final FlushClearRepository repository;

  public FlushClearDemoService(FlushClearRepository repository) {
    this.repository = repository;
  }

  public FlushClearResult demonstrate() {
    return repository.demonstrate();
  }
}