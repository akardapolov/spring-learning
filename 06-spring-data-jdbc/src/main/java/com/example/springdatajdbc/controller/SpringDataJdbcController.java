package com.example.springdatajdbc.controller;

import com.example.springdatajdbc.model.ApiModels.ApiResponse;
import com.example.springdatajdbc.model.ApiModels.CategoryCreateRequest;
import com.example.springdatajdbc.model.ApiModels.CategoryUpdateRequest;
import com.example.springdatajdbc.model.ApiModels.CustomerCreateRequest;
import com.example.springdatajdbc.model.ApiModels.OrderCreateRequest;
import com.example.springdatajdbc.model.ApiModels.ProductCreateRequest;
import com.example.springdatajdbc.model.ApiModels.ProductUpdateRequest;
import com.example.springdatajdbc.service.SpringDataJdbcDemoService;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jdbc")
public class SpringDataJdbcController {

  private final SpringDataJdbcDemoService service;

  public SpringDataJdbcController(SpringDataJdbcDemoService service) {
    this.service = service;
  }

  @GetMapping("/differences")
  public ApiResponse<?> differences() {
    return ApiResponse.ok(service.differencesDemo());
  }

  @GetMapping("/ddd")
  public ApiResponse<?> ddd() {
    return ApiResponse.ok(service.dddDemo());
  }

  @GetMapping("/categories")
  public ApiResponse<?> allCategories() {
    return ApiResponse.ok(service.getAllCategories());
  }

  @GetMapping("/categories/{id}")
  public ApiResponse<?> category(@PathVariable Long id) {
    return ApiResponse.ok(service.getCategory(id));
  }

  @PostMapping("/categories")
  public ApiResponse<?> createCategory(@RequestBody CategoryCreateRequest request) {
    return ApiResponse.ok(service.createCategory(request));
  }

  @PutMapping("/categories/{id}")
  public ApiResponse<?> updateCategory(@PathVariable Long id,
                                       @RequestBody CategoryUpdateRequest request) {
    return ApiResponse.ok(service.updateCategory(id, request));
  }

  @DeleteMapping("/categories/{id}")
  public ApiResponse<?> deleteCategory(@PathVariable Long id) {
    service.deleteCategory(id);
    return ApiResponse.ok("deleted");
  }

  @PostMapping("/categories/{id}/products")
  public ApiResponse<?> addProduct(@PathVariable Long id,
                                   @RequestBody ProductCreateRequest request) {
    return ApiResponse.ok(service.addProduct(id, request));
  }

  @PutMapping("/categories/{catId}/products/{prodId}")
  public ApiResponse<?> updateProduct(@PathVariable Long catId,
                                      @PathVariable Long prodId,
                                      @RequestBody ProductUpdateRequest request) {
    return ApiResponse.ok(service.updateProduct(catId, prodId, request));
  }

  @DeleteMapping("/categories/{catId}/products/{prodId}")
  public ApiResponse<?> removeProduct(@PathVariable Long catId,
                                      @PathVariable Long prodId) {
    return ApiResponse.ok(service.removeProduct(catId, prodId));
  }

  @GetMapping("/customers/{id}")
  public ApiResponse<?> customer(@PathVariable Long id) {
    return ApiResponse.ok(service.getCustomer(id));
  }

  @PostMapping("/customers")
  public ApiResponse<?> createCustomer(@RequestBody CustomerCreateRequest request) {
    return ApiResponse.ok(service.createCustomer(request));
  }

  @PostMapping("/customers/{id}/orders")
  public ApiResponse<?> addOrder(@PathVariable Long id,
                                 @RequestBody OrderCreateRequest request) {
    return ApiResponse.ok(service.addOrder(id, request));
  }

  @GetMapping("/custom-query")
  public ApiResponse<?> customQuery(@RequestParam String title) {
    return ApiResponse.ok(service.customQueryDemo(title));
  }

  @GetMapping("/custom-query/min-price")
  public ApiResponse<?> customQueryMinPrice(@RequestParam BigDecimal minPrice) {
    return ApiResponse.ok(service.customQueryByMinPrice(minPrice));
  }

  @GetMapping("/lifecycle")
  public ApiResponse<?> lifecycle() {
    return ApiResponse.ok(service.lifecycleDemo());
  }

  @GetMapping("/glossary")
  public ApiResponse<?> glossary() {
    return ApiResponse.ok(service.glossaryDemo());
  }

  @GetMapping("/endpoints")
  public ApiResponse<?> endpoints() {
    return ApiResponse.ok(service.endpointsDemo());
  }
}
