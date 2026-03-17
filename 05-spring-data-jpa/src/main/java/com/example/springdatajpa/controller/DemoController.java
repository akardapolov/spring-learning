package com.example.springdatajpa.controller;

import com.example.springdatajpa.model.ApiModels.ApiResponse;
import com.example.springdatajpa.model.ApiModels.PageResponse;
import com.example.springdatajpa.model.ApiModels.ProjectionDemoResult;
import com.example.springdatajpa.model.ApiModels.QueryAnnotationResult;
import com.example.springdatajpa.model.ApiModels.QueryMethodsResult;
import com.example.springdatajpa.model.ApiModels.RepositoryHierarchyResult;
import com.example.springdatajpa.model.ApiModels.SliceResponse;
import com.example.springdatajpa.model.ApiModels.SpecificationDemoResult;
import com.example.springdatajpa.service.ComplexQueryDemoService;
import com.example.springdatajpa.service.SpringDataDemoService;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/demo")
@RestController
public class DemoController {

  private static final int MAX_PAGE_SIZE = 100;

  private final SpringDataDemoService service;
  private final ComplexQueryDemoService complexQueryService;

  public DemoController(SpringDataDemoService service,
                        ComplexQueryDemoService complexQueryService) {
    this.service = service;
    this.complexQueryService = complexQueryService;
  }

  @GetMapping("/repository-hierarchy")
  public ApiResponse<RepositoryHierarchyResult> repositoryHierarchy() {
    return ApiResponse.ok(service.repositoryHierarchyDemo());
  }

  @GetMapping("/query-methods")
  public ApiResponse<QueryMethodsResult> queryMethods() {
    return ApiResponse.ok(service.queryMethodsDemo());
  }

  @GetMapping("/query-annotation")
  public ApiResponse<QueryAnnotationResult> queryAnnotation() {
    return ApiResponse.ok(service.queryAnnotationDemo());
  }

  @GetMapping("/pagination/page")
  public ApiResponse<PageResponse> page(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size
  ) {
    validatePagination(page, size);
    return ApiResponse.ok(service.pageDemo(page, size));
  }

  @GetMapping("/pagination/slice")
  public ApiResponse<SliceResponse> slice(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "2") int size
  ) {
    validatePagination(page, size);
    return ApiResponse.ok(service.sliceDemo(page, size));
  }

  @GetMapping("/projections")
  public ApiResponse<ProjectionDemoResult> projections() {
    return ApiResponse.ok(service.projectionDemo());
  }

  @GetMapping("/specifications")
  public ApiResponse<SpecificationDemoResult> specifications(
      @RequestParam(required = false) String type,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) java.math.BigDecimal minPrice
  ) {
    return ApiResponse.ok(service.specificationDemo(type, name, minPrice));
  }

  @GetMapping("/api/demo/complex-queries")
  public Map<String, Object> complexQueries(
      @RequestParam(defaultValue = "DEVICE") String type) {
    Map<String, Object> result = new LinkedHashMap<>();
    result.put("1_joinFetch", complexQueryService.demoJoinFetch(type));
    result.put("2_dtoProjection", complexQueryService.demoDtoProjection(type));
    result.put("3_nestedProjection", complexQueryService.demoNestedProjection(type));
    result.put("4_tupleAggregation", complexQueryService.demoTupleAggregation());
    result.put("5_nativeComplex", complexQueryService.demoNativeComplex(type));
    result.put("6_assembled", complexQueryService.demoAssembledResponse());
    return result;
  }

  private void validatePagination(int page, int size) {
    if (page < 0) {
      throw new IllegalArgumentException("page must be >= 0");
    }
    if (size < 1 || size > MAX_PAGE_SIZE) {
      throw new IllegalArgumentException("size must be between 1 and " + MAX_PAGE_SIZE);
    }
  }
}