package com.example.springrest.controller;

import com.example.springrest.dto.CategoryDto;
import com.example.springrest.model.ApiModels.CategoryWithProductsResponse;
import com.example.springrest.service.RestDemoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest/categories")
public class CategoryController {

  private final RestDemoService demoService;

  public CategoryController(RestDemoService demoService) {
    this.demoService = demoService;
  }

  // GET /api/rest/categories - list all categories
  @GetMapping
  public List<CategoryDto> getAllCategories() {
    return demoService.getAllCategories();
  }

  // GET /api/rest/categories/{id} - get category by id
  @GetMapping("/{id}")
  public CategoryDto getCategoryById(@PathVariable Long id) {
    return demoService.getCategoryById(id);
  }

  // GET /api/rest/categories/{id}/products - get category with products
  @GetMapping("/{id}/products")
  public CategoryWithProductsResponse getCategoryWithProducts(@PathVariable Long id) {
    return demoService.getCategoryWithProducts(id);
  }

  // POST /api/rest/categories - create category
  @PostMapping
  public ResponseEntity<com.example.springrest.model.ApiModels.CrudOperationResult> createCategory(
      @RequestBody CreateCategoryRequest request) {

    var result = demoService.createCategory(request.title());
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  public record CreateCategoryRequest(String title) {
  }
}
