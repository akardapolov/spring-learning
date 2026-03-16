package com.example.hibernate.springdata;

import com.example.hibernate.entity.Category;
import com.example.hibernate.entity.Product;
import com.example.hibernate.model.ApiModels.SpringDataEntityGraphResult;
import com.example.hibernate.model.ApiModels.SpringDataPaginationResult;
import com.example.hibernate.model.ApiModels.SpringDataQueryMethodsResult;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SpringDataService {

  private final ProductSpringDataRepository productRepository;
  private final CategorySpringDataRepository categoryRepository;

  public SpringDataService(ProductSpringDataRepository productRepository,
                           CategorySpringDataRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  public SpringDataQueryMethodsResult queryMethodsDemo() {
    List<Product> devices = productRepository.findByTypeOrderByName("DEVICE");
    List<Product> electronics = productRepository.findByCategory_Title("Electronics");
    return new SpringDataQueryMethodsResult(devices.size(), electronics.size());
  }

  public SpringDataEntityGraphResult queryAndEntityGraphDemo() {
    Category category = categoryRepository.findDetailedByTitle("Electronics").orElseThrow();
    List<Category> categories = categoryRepository.findAllWithProducts();
    return new SpringDataEntityGraphResult(category.getProducts().size(), categories.size());
  }

  public SpringDataPaginationResult paginationDemo() {
    Page<Product> page = productRepository.findByPriceGreaterThan(
        BigDecimal.valueOf(20),
        PageRequest.of(0, 3)
    );

    return new SpringDataPaginationResult(
        page.getSize(),
        page.getTotalElements(),
        page.getContent().size()
    );
  }
}