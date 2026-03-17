package com.example.springdatajpa.service;

import com.example.springdatajpa.entity.Product;
import com.example.springdatajpa.model.ApiModels.PageResponse;
import com.example.springdatajpa.model.ApiModels.ProductShortDto;
import com.example.springdatajpa.model.ApiModels.ProjectionDemoResult;
import com.example.springdatajpa.model.ApiModels.ProjectionItem;
import com.example.springdatajpa.model.ApiModels.QueryAnnotationResult;
import com.example.springdatajpa.model.ApiModels.QueryMethodsResult;
import com.example.springdatajpa.model.ApiModels.RepositoryHierarchyResult;
import com.example.springdatajpa.model.ApiModels.SliceResponse;
import com.example.springdatajpa.model.ApiModels.SpecificationDemoResult;
import com.example.springdatajpa.repository.ProductRepository;
import com.example.springdatajpa.repository.QueryProductRepository;
import com.example.springdatajpa.repository.projection.ProductView;
import com.example.springdatajpa.repository.specification.ProductSpecification;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Два репозитория для Product ({@link ProductRepository} и {@link QueryProductRepository})
 * ProductRepository        —   демонстрирует query methods, проекции, спецификации и пагинацию,
 * QueryProductRepository   —   код по работе с {@code @Query} (JPQL и native SQL).
 */
@Service
@Transactional(readOnly = true)
public class SpringDataDemoService {

  private final ProductRepository productRepository;
  private final QueryProductRepository queryProductRepository;

  public SpringDataDemoService(ProductRepository productRepository,
                               QueryProductRepository queryProductRepository) {
    this.productRepository = productRepository;
    this.queryProductRepository = queryProductRepository;
  }

  public RepositoryHierarchyResult repositoryHierarchyDemo() {
    return new RepositoryHierarchyResult(
        ProductRepository.class.getSimpleName(),
        org.springframework.data.jpa.repository.JpaRepository.class.getSimpleName(),
        org.springframework.data.repository.ListPagingAndSortingRepository.class.getSimpleName(),
        org.springframework.data.repository.ListCrudRepository.class.getSimpleName(),
        org.springframework.data.repository.Repository.class.getSimpleName(),
        List.of(
            "Repository",
            "CrudRepository",
            "ListCrudRepository",
            "PagingAndSortingRepository",
            "ListPagingAndSortingRepository",
            "JpaRepository"
        )
    );
  }

  public QueryMethodsResult queryMethodsDemo() {
    List<Product> devices = productRepository.findByTypeOrderByName("DEVICE");
    List<Product> electronics = productRepository.findByCategory_Title("Electronics");
    List<Product> expensive = productRepository.findByPriceGreaterThanOrderByPriceDesc(BigDecimal.valueOf(100));

    String firstDeviceName = devices.isEmpty() ? null : devices.getFirst().getName();

    return new QueryMethodsResult(
        devices.size(),
        electronics.size(),
        firstDeviceName,
        expensive.size()
    );
  }

  public QueryAnnotationResult queryAnnotationDemo() {
    List<Product> jpql = queryProductRepository.findExpensiveByType("DEVICE", BigDecimal.valueOf(500));
    List<Product> nativeProducts = queryProductRepository.findNativeByType("DEVICE");

    String firstJpqlName = jpql.isEmpty() ? null : jpql.getFirst().getName();
    String firstNativeName = nativeProducts.isEmpty() ? null : nativeProducts.getFirst().getName();

    return new QueryAnnotationResult(
        jpql.size(),
        nativeProducts.size(),
        firstJpqlName,
        firstNativeName
    );
  }

  public PageResponse pageDemo(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
    Page<Product> resultPage = productRepository.findByPriceGreaterThan(BigDecimal.ZERO, pageable);

    List<ProductShortDto> content = resultPage.getContent().stream()
        .map(this::toShortDto)
        .toList();

    return new PageResponse(
        resultPage.getNumber(),
        resultPage.getSize(),
        resultPage.getTotalElements(),
        resultPage.getTotalPages(),
        resultPage.getContent().size(),
        content
    );
  }

  public SliceResponse sliceDemo(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
    Slice<Product> slice = productRepository.findByType("DEVICE", pageable);

    List<ProductShortDto> content = slice.getContent().stream()
        .map(this::toShortDto)
        .toList();

    return new SliceResponse(
        slice.getNumber(),
        slice.getSize(),
        slice.hasNext(),
        slice.getNumberOfElements(),
        content
    );
  }

  public ProjectionDemoResult projectionDemo() {
    List<ProductView> projections = productRepository.findProjectedByTypeOrderByName("DEVICE");

    List<ProjectionItem> items = projections.stream()
        .map(p -> new ProjectionItem(p.getId(), p.getName(), p.getPrice()))
        .toList();

    String firstName = projections.isEmpty() ? null : projections.getFirst().getName();

    return new ProjectionDemoResult(
        projections.size(),
        firstName,
        items
    );
  }

  public SpecificationDemoResult specificationDemo(String type, String name, BigDecimal minPrice) {
    List<Specification<Product>> specs = new ArrayList<>();

    if (type != null && !type.isBlank()) {
      specs.add(ProductSpecification.hasType(type));
    }
    if (name != null && !name.isBlank()) {
      specs.add(ProductSpecification.nameContains(name));
    }
    if (minPrice != null) {
      specs.add(ProductSpecification.priceGreaterThan(minPrice));
    }

    Specification<Product> spec = Specification.allOf(specs);

    List<ProductShortDto> items = productRepository.findAll(spec, Sort.by("name")).stream()
        .map(this::toShortDto)
        .toList();

    return new SpecificationDemoResult(items.size(), items);
  }

  private ProductShortDto toShortDto(Product product) {
    return new ProductShortDto(product.getId(), product.getName(), product.getPrice());
  }
}