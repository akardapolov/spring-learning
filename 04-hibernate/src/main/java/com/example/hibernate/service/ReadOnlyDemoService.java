package com.example.hibernate.service;

import com.example.hibernate.entity.Product;
import com.example.hibernate.model.ApiModels.ReadOnlyInspectionResult;
import com.example.hibernate.repository.FetchingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReadOnlyDemoService {

  private final FetchingRepository fetchingRepository;

  public ReadOnlyDemoService(FetchingRepository fetchingRepository) {
    this.fetchingRepository = fetchingRepository;
  }

  @Transactional(readOnly = true)
  public ReadOnlyInspectionResult inspect(Long id) {
    Product product = fetchingRepository.findProduct(id);
    return new ReadOnlyInspectionResult(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getCategory().getTitle()
    );
  }
}