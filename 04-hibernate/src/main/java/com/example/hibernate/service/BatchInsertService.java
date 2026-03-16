package com.example.hibernate.service;

import com.example.hibernate.model.ApiModels.BatchInsertResult;
import com.example.hibernate.repository.BatchInsertRepository;
import org.springframework.stereotype.Service;

@Service
public class BatchInsertService {

  private final BatchInsertRepository repository;

  public BatchInsertService(BatchInsertRepository repository) {
    this.repository = repository;
  }

  public BatchInsertResult insert(int count) {
    int inserted = repository.insertProducts(count);
    return new BatchInsertResult(inserted, 20);
  }
}