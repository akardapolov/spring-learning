package com.example.hibernate.service;

import com.example.hibernate.entity.Category;
import com.example.hibernate.model.ApiModels.NPlusOneResult;
import com.example.hibernate.repository.NPlusOneRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NPlusOneDemoService {

  private final NPlusOneRepository repository;

  public NPlusOneDemoService(NPlusOneRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public NPlusOneResult lazyScenario() {
    List<Category> categories = repository.findAllLazy();
    int productCount = categories.stream().mapToInt(category -> category.getProducts().size()).sum();
    return result("lazy", categories.size(), productCount);
  }

  @Transactional(readOnly = true)
  public NPlusOneResult joinFetchScenario() {
    List<Category> categories = repository.findAllJoinFetch();
    int productCount = categories.stream().mapToInt(category -> category.getProducts().size()).sum();
    return result("join-fetch", categories.size(), productCount);
  }

  @Transactional(readOnly = true)
  public NPlusOneResult entityGraphScenario() {
    List<Category> categories = repository.findAllWithEntityGraph();
    int productCount = categories.stream().mapToInt(category -> category.getProducts().size()).sum();
    return result("entity-graph", categories.size(), productCount);
  }

  @Transactional(readOnly = true)
  public NPlusOneResult subselectScenario() {
    List<Category> categories = repository.findAllSubselectAndBatch();
    int productCount = categories.stream().mapToInt(category -> category.getProducts().size()).sum();
    return result("subselect-batch", categories.size(), productCount);
  }

  private NPlusOneResult result(String scenario, int categoryCount, int productCount) {
    return new NPlusOneResult(scenario, categoryCount, productCount);
  }
}