package com.example.hibernate.service;

import com.example.hibernate.entity.Category;
import com.example.hibernate.model.ApiModels.LazyExceptionResult;
import com.example.hibernate.repository.LazyExceptionRepository;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Service;

@Service
public class LazyExceptionDemoService {

  private final LazyExceptionRepository repository;

  public LazyExceptionDemoService(LazyExceptionRepository repository) {
    this.repository = repository;
  }

  public LazyExceptionResult demonstrate(Long id) {
    Category category = repository.loadCategory(id);

    try {
      int size = category.getProducts().size();
      return new LazyExceptionResult(
          category.getId(),
          category.getTitle(),
          size,
          false,
          null
      );
    } catch (LazyInitializationException ex) {
      return new LazyExceptionResult(
          category.getId(),
          category.getTitle(),
          null,
          true,
          ex.getClass().getSimpleName()
      );
    }
  }
}