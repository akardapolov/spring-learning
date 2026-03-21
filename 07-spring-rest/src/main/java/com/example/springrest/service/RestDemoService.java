package com.example.springrest.service;

import com.example.springrest.dto.CategoryDto;
import com.example.springrest.dto.ProductCreateRequest;
import com.example.springrest.dto.ProductDto;
import com.example.springrest.dto.ProductUpdateRequest;
import com.example.springrest.entity.Category;
import com.example.springrest.entity.Product;
import com.example.springrest.exception.CategoryNotFoundException;
import com.example.springrest.exception.ProductNotFoundException;
import com.example.springrest.model.ApiModels;
import com.example.springrest.model.ApiModels.ControllerBasicsResult;
import com.example.springrest.model.ApiModels.ExceptionHandlingDemo;
import com.example.springrest.model.ApiModels.PaginationResult;
import com.example.springrest.model.ApiModels.SearchCriteria;
import com.example.springrest.repository.CategoryRepository;
import com.example.springrest.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestDemoService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public RestDemoService(ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  // Section 1: Controller Basics
  public ControllerBasicsResult getControllerBasics() {
    Map<String, String> httpMethodMappings = new HashMap<>();
    httpMethodMappings.put("GET", "Read resources");
    httpMethodMappings.put("POST", "Create resources");
    httpMethodMappings.put("PUT", "Update entire resource");
    httpMethodMappings.put("PATCH", "Partial update");
    httpMethodMappings.put("DELETE", "Delete resources");

    List<String> annotations = List.of(
        "@RestController",
        "@RequestMapping",
        "@GetMapping",
        "@PostMapping",
        "@PutMapping",
        "@PatchMapping",
        "@DeleteMapping",
        "@PathVariable",
        "@RequestParam",
        "@RequestBody",
        "@ResponseEntity"
    );

    return new ControllerBasicsResult("RestController", annotations, httpMethodMappings);
  }

  // Section 2: Request Parameters
  public ApiModels.RequestParametersDemo demonstrateParameters(
      String pathVariable, String queryParam, Map<String, String> headers, Object body) {

    return new ApiModels.RequestParametersDemo(pathVariable, queryParam, headers, body);
  }

  // Section 3: Response Handling
  public ApiModels.ResponseHandlingDemo demonstrateResponse(String responseType) {
    int statusCode = switch (responseType) {
      case "ok" -> 200;
      case "created" -> 201;
      case "no_content" -> 204;
      case "bad_request" -> 400;
      case "not_found" -> 404;
      case "server_error" -> 500;
      default -> 200;
    };

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("X-Custom-Header", "demo-value");

    return new ApiModels.ResponseHandlingDemo(
        statusCode,
        getHttpStatusText(statusCode),
        headers,
        Map.of("message", "Demo response for: " + responseType)
    );
  }

  private String getHttpStatusText(int statusCode) {
    return switch (statusCode) {
      case 200 -> "OK";
      case 201 -> "Created";
      case 204 -> "No Content";
      case 400 -> "Bad Request";
      case 404 -> "Not Found";
      case 500 -> "Internal Server Error";
      default -> "Unknown";
    };
  }

  // Section 4: Exception Handling
  public ExceptionHandlingDemo demonstrateExceptionHandling(String exceptionType) {
    return switch (exceptionType) {
      case "not_found" ->
          new ExceptionHandlingDemo("ProductNotFoundException", "Product not found", 404);
      case "validation" ->
          new ExceptionHandlingDemo("ValidationException", "Validation failed", 400);
      case "bad_request" ->
          new ExceptionHandlingDemo("IllegalArgumentException", "Invalid argument", 400);
      default ->
          new ExceptionHandlingDemo("Exception", "Unexpected error", 500);
    };
  }

  // Section 5: Validation
  public ApiModels.ValidationDemo demonstrateValidation(ProductCreateRequest request) {
    // Simulate validation
    java.util.ArrayList<String> violations = new java.util.ArrayList<>();

    if (request.name() == null || request.name().isBlank()) {
      violations.add("Name is required");
    } else if (request.name().length() < 2) {
      violations.add("Name must be at least 2 characters");
    }

    if (request.price() == null || request.price().compareTo(BigDecimal.ZERO) <= 0) {
      violations.add("Price must be greater than 0");
    }

    return new ApiModels.ValidationDemo(violations.isEmpty(), violations.toArray(new String[0]));
  }

  // Section 6: CRUD Operations
  @Transactional
  public ApiModels.CrudOperationResult createProduct(ProductCreateRequest request) {
    Product product = new Product(
        request.name(),
        request.type(),
        request.price()
    );

    if (request.categoryId() != null) {
      Category category = categoryRepository.findById(request.categoryId())
          .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));
      product.setCategory(category);
    }

    if (request.manufacturer() != null || request.sku() != null) {
      product.setDetails(new com.example.springrest.entity.ProductDetails(
          request.manufacturer(),
          request.sku()
      ));
    }

    Product saved = productRepository.save(product);
    return new ApiModels.CrudOperationResult("CREATE", toDto(saved), "Product created successfully");
  }

  public ProductDto getProductById(Long id) {
    Product product = productRepository.findWithCategoryById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));
    return toDto(product);
  }

  public List<ProductDto> getAllProducts() {
    return productRepository.findAllWithCategory().stream()
        .map(this::toDto)
        .toList();
  }

  @Transactional
  public ApiModels.CrudOperationResult updateProduct(Long id, ProductUpdateRequest request) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));

    if (request.name() != null) {
      product.setName(request.name());
    }
    if (request.type() != null) {
      product.setType(request.type());
    }
    if (request.price() != null) {
      product.setPrice(request.price());
    }
    if (request.categoryId() != null) {
      Category category = categoryRepository.findById(request.categoryId())
          .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));
      product.setCategory(category);
    }

    if (request.manufacturer() != null || request.sku() != null) {
      if (product.getDetails() == null) {
        product.setDetails(new com.example.springrest.entity.ProductDetails());
      }
      if (request.manufacturer() != null) {
        product.getDetails().setManufacturer(request.manufacturer());
      }
      if (request.sku() != null) {
        product.getDetails().setSku(request.sku());
      }
    }

    Product saved = productRepository.save(product);
    return new ApiModels.CrudOperationResult("UPDATE", toDto(saved), "Product updated successfully");
  }

  @Transactional
  public ApiModels.CrudOperationResult deleteProduct(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));
    productRepository.delete(product);
    return new ApiModels.CrudOperationResult("DELETE", toDto(product), "Product deleted successfully");
  }

  // Section 7: Pagination and Sorting
  public PaginationResult getPaginatedProducts(int page, int size, String sortBy, String sortDir) {
    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Product> productPage = productRepository.findAll(pageable);

    List<ProductDto> content = productPage.getContent().stream()
        .map(this::toDto)
        .collect(Collectors.toList());

    return new PaginationResult(
        productPage.getNumber(),
        productPage.getSize(),
        productPage.getTotalElements(),
        productPage.getTotalPages(),
        content
    );
  }

  public PaginationResult searchProducts(SearchCriteria criteria, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

    Page<Product> result;
    if (criteria.type() != null && criteria.minPrice() != null) {
      result = productRepository.findByTypeAndPriceGreaterThan(
          criteria.type(), criteria.minPrice(), pageable);
    } else if (criteria.type() != null) {
      result = productRepository.findByType(criteria.type(), pageable);
    } else if (criteria.categoryId() != null) {
      List<Product> byCategory = productRepository.findByCategoryId(criteria.categoryId());
      result = new org.springframework.data.domain.PageImpl<>(
          byCategory, pageable, byCategory.size());
    } else {
      result = productRepository.findAll(pageable);
    }

    List<ProductDto> content = result.getContent().stream()
        .map(this::toDto)
        .collect(Collectors.toList());

    return new PaginationResult(
        result.getNumber(),
        result.getSize(),
        result.getTotalElements(),
        result.getTotalPages(),
        content
    );
  }

  // Category operations
  @Transactional
  public ApiModels.CrudOperationResult createCategory(String title) {
    Category category = new Category(title);
    Category saved = categoryRepository.save(category);
    return new ApiModels.CrudOperationResult(
        "CREATE",
        new ProductDto(saved.getId(), saved.getTitle(), null, null, null, null, null, null),
        "Category created successfully"
    );
  }

  public CategoryDto getCategoryById(Long id) {
    return categoryRepository.findById(id)
        .map(this::toCategoryDto)
        .orElseThrow(() -> new CategoryNotFoundException(id));
  }

  public ApiModels.CategoryWithProductsResponse getCategoryWithProducts(Long id) {
    Category category = categoryRepository.findWithProductsById(id)
        .orElseThrow(() -> new CategoryNotFoundException(id));

    List<ProductDto> products = category.getProducts().stream()
        .map(this::toDto)
        .toList();

    return new ApiModels.CategoryWithProductsResponse(toCategoryDto(category), products);
  }

  public List<CategoryDto> getAllCategories() {
    return categoryRepository.findAll().stream()
        .map(this::toCategoryDto)
        .toList();
  }

  // Helper methods
  private ProductDto toDto(Product product) {
    CategoryDto category = product.getCategory() != null
        ? toCategoryDto(product.getCategory())
        : null;

    return new ProductDto(
        product.getId(),
        product.getName(),
        product.getType(),
        product.getPrice(),
        product.getVersion(),
        product.getDetails() != null ? product.getDetails().getManufacturer() : null,
        product.getDetails() != null ? product.getDetails().getSku() : null,
        category
    );
  }

  private CategoryDto toCategoryDto(Category category) {
    return new CategoryDto(category.getId(), category.getTitle());
  }
}
