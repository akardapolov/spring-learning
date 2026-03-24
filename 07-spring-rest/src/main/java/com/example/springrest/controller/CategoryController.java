package com.example.springrest.controller;

import com.example.springrest.dto.CategoryDto;
import com.example.springrest.model.ApiModels.CategoryWithProductsResponse;
import com.example.springrest.service.RestDemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Categories", description = "Category management API - view categories and their products")
@RestController
@RequestMapping("/api/rest/categories")
public class CategoryController {

  private final RestDemoService demoService;

  public CategoryController(RestDemoService demoService) {
    this.demoService = demoService;
  }

  @Operation(summary = "Get all categories", description = "Returns a list of all available categories")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved categories")
  @GetMapping
  public List<CategoryDto> getAllCategories() {
    return demoService.getAllCategories();
  }

  @Operation(summary = "Get category by ID", description = "Returns a single category by its unique identifier")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category found"),
      @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = com.example.springrest.dto.ErrorResponse.class)))
  })
  @GetMapping("/{id}")
  public CategoryDto getCategoryById(
      @Parameter(description = "Category ID", example = "1", required = true)
      @PathVariable Long id) {
    return demoService.getCategoryById(id);
  }

  @Operation(summary = "Get category with products", description = "Returns a category along with all its associated products")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category with products found"),
      @ApiResponse(responseCode = "404", description = "Category not found")
  })
  @GetMapping("/{id}/products")
  public CategoryWithProductsResponse getCategoryWithProducts(
      @Parameter(description = "Category ID", example = "1", required = true)
      @PathVariable Long id) {
    return demoService.getCategoryWithProducts(id);
  }

  @Operation(summary = "Create a new category", description = "Creates a new category and returns the created category with its assigned ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Category created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PostMapping
  public ResponseEntity<com.example.springrest.model.ApiModels.CrudOperationResult> createCategory(
      @RequestBody CreateCategoryRequest request) {

    var result = demoService.createCategory(request.title());
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Schema(description = "Request object for creating a new category")
  public record CreateCategoryRequest(
      @Schema(description = "Category title", example = "Electronics", required = true)
      @NotBlank(message = "Title is required")
      @Size(min = 2, max = 50, message = "Title must be between 2 and 50 characters")
      String title) {
  }
}
