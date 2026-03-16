package com.example.hibernate.service;

import com.example.hibernate.model.ApiModels.RelationshipSummaryResult;
import com.example.hibernate.repository.RelationshipRepository;
import org.springframework.stereotype.Service;

@Service
public class RelationshipDemoService {

  private final RelationshipRepository repository;

  public RelationshipDemoService(RelationshipRepository repository) {
    this.repository = repository;
  }

  public RelationshipSummaryResult summarizeRelationships() {
    int categories = repository.findAllCategories().size();
    int tags = repository.findAllTags().size();
    int customers = repository.findAllCustomers().size();
    return new RelationshipSummaryResult(categories, tags, customers);
  }
}