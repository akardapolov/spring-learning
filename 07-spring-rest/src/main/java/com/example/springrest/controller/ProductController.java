package com.example.springrest.controller;

import com.example.springrest.dto.ProductCreateRequest;
import com.example.springrest.dto.ProductDto;
import com.example.springrest.dto.ProductUpdateRequest;
import com.example.springrest.model.ApiModels;
import com.example.springrest.model.ApiModels.CrudOperationResult;
import com.example.springrest.model.ApiModels.PaginationResult;
import com.example.springrest.service.RestDemoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest/products")
public class ProductController {

  private final RestDemoService demoService;

  public ProductController(RestDemoService demoService) {
    this.demoService = demoService;
  }

  // GET /api/rest/products - list all products
  @GetMapping
  public List<ProductDto> getAllProducts() {
    return demoService.getAllProducts();
  }

  // GET /api/rest/products/{id} - get product by id
  @GetMapping("/{id}")
  public ProductDto getProductById(@PathVariable Long id) {
    return demoService.getProductById(id);
  }

  // POST /api/rest/products - create product
  @PostMapping
  public ResponseEntity<CrudOperationResult> createProduct(
      @Valid @RequestBody ProductCreateRequest request) {

    CrudOperationResult result = demoService.createProduct(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  // PUT /api/rest/products/{id} - full update
  @PutMapping("/{id}")
  public CrudOperationResult updateProduct(
      @PathVariable Long id,
      @Valid @RequestBody ProductUpdateRequest request) {

    return demoService.updateProduct(id, request);
  }

  // PATCH /api/rest/products/{id} - partial update
  @PatchMapping("/{id}")
  public CrudOperationResult patchProduct(
      @PathVariable Long id,
      @RequestBody ProductUpdateRequest request) {

    // For PATCH, we only update non-null fields
    return demoService.updateProduct(id, request);
  }

  // DELETE /api/rest/products/{id} - delete product
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    demoService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  // GET /api/rest/products/search - search with pagination
  @GetMapping("/search")
  public PaginationResult searchProducts(
      @RequestParam(required = false) String type,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) java.math.BigDecimal minPrice,
      @RequestParam(required = false) java.math.BigDecimal maxPrice,
      @RequestParam(required = false) Long categoryId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    ApiModels.SearchCriteria criteria = new ApiModels.SearchCriteria(
        type, name, minPrice, maxPrice, categoryId);

    return demoService.searchProducts(criteria, page, size);
  }

  // GET /api/rest/products/paginated - paginated list
  @GetMapping("/paginated")
  public PaginationResult getPaginatedProducts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "name") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir) {

    return demoService.getPaginatedProducts(page, size, sortBy, sortDir);
  }
}
