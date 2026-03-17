package com.example.springdatajpa.model;

import java.math.BigDecimal;
import java.util.List;

public final class ApiModels {

  private ApiModels() {
  }

  public record ApiResponse<T>(
      boolean success,
      T data
  ) {
    public static <T> ApiResponse<T> ok(T data) {
      return new ApiResponse<>(true, data);
    }
  }

  public record ApiErrorResponse(
      boolean success,
      String errorCode,
      String message
  ) {
    public static ApiErrorResponse of(String errorCode, String message) {
      return new ApiErrorResponse(false, errorCode, message);
    }
  }

  public record RepositoryHierarchyResult(
      String repositoryInterface,
      String jpaRepositoryInterface,
      String listPagingAndSortingRepositoryInterface,
      String listCrudRepositoryInterface,
      String rootRepositoryInterface,
      List<String> hierarchy
  ) {
  }

  public record ProductShortDto(
      Long id,
      String name,
      BigDecimal price
  ) {
  }

  public record QueryMethodsResult(
      int deviceCount,
      int electronicsCount,
      String firstDeviceName,
      int expensiveCount
  ) {
  }

  public record QueryAnnotationResult(
      int jpqlCount,
      int nativeCount,
      String firstJpqlName,
      String firstNativeName
  ) {
  }

  public record PageResponse(
      int pageNumber,
      int pageSize,
      long totalElements,
      int totalPages,
      int contentSize,
      List<ProductShortDto> content
  ) {
  }

  public record SliceResponse(
      int pageNumber,
      int pageSize,
      boolean hasNext,
      int contentSize,
      List<ProductShortDto> content
  ) {
  }

  public record ProjectionItem(
      Long id,
      String name,
      BigDecimal price
  ) {
  }

  public record ProjectionDemoResult(
      int count,
      String firstName,
      List<ProjectionItem> items
  ) {
  }

  public record SpecificationDemoResult(
      int matchedCount,
      List<ProductShortDto> items
  ) {
  }
}