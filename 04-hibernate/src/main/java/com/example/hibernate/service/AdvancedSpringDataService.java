package com.example.hibernate.service;

import com.example.hibernate.entity.Product;
import com.example.hibernate.model.ApiModels.ProjectionDemoResult;
import com.example.hibernate.model.ApiModels.SpecificationDemoResult;
import com.example.hibernate.springdata.ProductSpecification;
import com.example.hibernate.springdata.ProductSpringDataRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AdvancedSpringDataService {

  private final ProductSpringDataRepository repository;

  public AdvancedSpringDataService(ProductSpringDataRepository repository) {
    this.repository = repository;
  }

  public SpecificationDemoResult specificationDemo() {
    Specification<Product> specification =
        ProductSpecification.hasType("DEVICE")
            .and(ProductSpecification.priceGreaterThan(BigDecimal.valueOf(500)));

    List<Product> products = repository.findAll(specification);
    return new SpecificationDemoResult(products.size());
  }

  public ProjectionDemoResult projectionDemo() {
    List<com.example.hibernate.springdata.ProductView> projections =
        repository.findProjectedByTypeOrderByName("DEVICE");

    String firstProjectionName = projections.isEmpty() ? null : projections.getFirst().getName();

    return new ProjectionDemoResult(
        projections.size(),
        firstProjectionName
    );
  }
}