package com.example.hibernate.service;

import com.example.hibernate.entity.Category;
import com.example.hibernate.entity.Product;
import com.example.hibernate.model.ApiModels.LazyCategoryResult;
import com.example.hibernate.model.ApiModels.LazyProductResult;
import com.example.hibernate.repository.FetchingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FetchingDemoService {

  private final FetchingRepository repository;

  public FetchingDemoService(FetchingRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public LazyCategoryResult lazyCategory(Long id) {
    Category category = repository.findCategory(id);
    return new LazyCategoryResult(
        category.getId(),
        category.getTitle(),
        category.getProducts().size()
    );
  }

  @Transactional(readOnly = true)
  public LazyProductResult lazyProduct(Long id) {
    Product product = repository.findProduct(id);
    return new LazyProductResult(
        product.getId(),
        product.getName(),
        product.getCategory().getTitle()
    );
  }
}