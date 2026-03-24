package com.example.springrest.controller;

import com.example.springrest.dto.ProductCreateRequest;
import com.example.springrest.dto.ProductDto;
import com.example.springrest.dto.ProductUpdateRequest;
import com.example.springrest.model.ApiModels;
import com.example.springrest.model.ApiModels.CrudOperationResult;
import com.example.springrest.model.ApiModels.PaginationResult;
import com.example.springrest.service.RestDemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Products", description = "Product management API - CRUD operations and search")
@RestController
@RequestMapping("/api/rest/products")
public class ProductController {

  private final RestDemoService demoService;

  public ProductController(RestDemoService demoService) {
    this.demoService = demoService;
  }

  @Operation(summary = "Get all products", description = "Returns a list of all available products")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved products")
  @GetMapping
  public List<ProductDto> getAllProducts() {
    return demoService.getAllProducts();
  }

  @Operation(summary = "Get product by ID", description = "Returns a single product by its unique identifier")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product found"),
      @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(implementation = com.example.springrest.dto.ErrorResponse.class)))
  })
  @GetMapping("/{id}")
  public ProductDto getProductById(
      @Parameter(description = "Product ID", example = "1", required = true)
      @PathVariable Long id) {
    return demoService.getProductById(id);
  }

  @Operation(summary = "Create a new product", description = "Creates a new product and returns the created product with its assigned ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Product created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = com.example.springrest.dto.ValidationError.class)))
  })
  @PostMapping
  public ResponseEntity<CrudOperationResult> createProduct(
      @Valid @RequestBody ProductCreateRequest request) {

    CrudOperationResult result = demoService.createProduct(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Operation(summary = "Update product (full)", description = "Updates all fields of an existing product. All fields are required.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product updated successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PutMapping("/{id}")
  public CrudOperationResult updateProduct(
      @Parameter(description = "Product ID", example = "1", required = true)
      @PathVariable Long id,
      @Valid @RequestBody ProductUpdateRequest request) {

    return demoService.updateProduct(id, request);
  }

  @Operation(summary = "Update product (partial)", description = "Updates only provided fields of an existing product. Null fields are ignored.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product updated successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @PatchMapping("/{id}")
  public CrudOperationResult patchProduct(
      @Parameter(description = "Product ID", example = "1", required = true)
      @PathVariable Long id,
      @RequestBody ProductUpdateRequest request) {

    // For PATCH, we only update non-null fields
    return demoService.updateProduct(id, request);
  }

  @Operation(summary = "Delete product", description = "Deletes a product by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(
      @Parameter(description = "Product ID", example = "1", required = true)
      @PathVariable Long id) {
    demoService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Search products", description = "Search products with filters and pagination")
  @GetMapping("/search")
  public PaginationResult searchProducts(
      @Parameter(description = "Filter by product type", example = "Electronics")
      @RequestParam(required = false) String type,

      @Parameter(description = "Filter by product name (partial match)", example = "Laptop")
      @RequestParam(required = false) String name,

      @Parameter(description = "Minimum price", example = "100.00")
      @RequestParam(required = false) java.math.BigDecimal minPrice,

      @Parameter(description = "Maximum price", example = "2000.00")
      @RequestParam(required = false) java.math.BigDecimal maxPrice,

      @Parameter(description = "Filter by category ID", example = "1")
      @RequestParam(required = false) Long categoryId,

      @Parameter(description = "Page number (0-indexed)", example = "0")
      @RequestParam(defaultValue = "0") int page,

      @Parameter(description = "Page size", example = "10")
      @RequestParam(defaultValue = "10") int size) {

    ApiModels.SearchCriteria criteria = new ApiModels.SearchCriteria(
        type, name, minPrice, maxPrice, categoryId);

    return demoService.searchProducts(criteria, page, size);
  }

  @Operation(summary = "Get paginated products", description = "Returns products with pagination and sorting")
  @GetMapping("/paginated")
  public PaginationResult getPaginatedProducts(
      @Parameter(description = "Page number (0-indexed)", example = "0")
      @RequestParam(defaultValue = "0") int page,

      @Parameter(description = "Page size", example = "10")
      @RequestParam(defaultValue = "10") int size,

      @Parameter(description = "Sort field", example = "name")
      @RequestParam(defaultValue = "name") String sortBy,

      @Parameter(description = "Sort direction (asc/desc)", example = "asc")
      @RequestParam(defaultValue = "asc") String sortDir) {

    return demoService.getPaginatedProducts(page, size, sortBy, sortDir);
  }
}
