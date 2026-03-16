package com.example.hibernate.service;

import com.example.hibernate.entity.Product;
import com.example.hibernate.model.ApiModels.CachingResult;
import com.example.hibernate.model.ApiModels.QueryCacheResult;
import com.example.hibernate.repository.CachingRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CachingDemoService {

  private final CachingRepository repository;

  public CachingDemoService(CachingRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public CachingResult firstLevelCache(Long id) {
    Product first = repository.findProduct(id);
    Product second = repository.findProduct(id);
    return new CachingResult(first == second, first.getName());
  }

  public QueryCacheResult queryCache(String type) {
    List<Product> first = repository.findProductsByTypeCached(type);
    List<Product> second = repository.findProductsByTypeCached(type);
    return new QueryCacheResult(first.size(), second.size(), type);
  }
}