package com.example.springrest.model;

import com.example.springrest.dto.CategoryDto;
import com.example.springrest.dto.ProductDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

  // Demo result records
  public record ControllerBasicsResult(
      String controllerType,
      List<String> availableAnnotations,
      Map<String, String> httpMethodMappings
  ) {
  }

  public record RequestParametersDemo(
      String pathVariable,
      String queryParam,
      Map<String, String> requestHeaders,
      Object requestBody
  ) {
  }

  public record ResponseHandlingDemo(
      int statusCode,
      String statusText,
      Map<String, String> responseHeaders,
      Object body
  ) {
  }

  public record ExceptionHandlingDemo(
      String exceptionType,
      String errorMessage,
      int statusCode
  ) {
  }

  public record ValidationDemo(
      boolean valid,
      String[] violations
  ) {
  }

  public record CrudOperationResult(
      String operation,
      ProductDto product,
      String message
  ) {
  }

  public record PaginationResult(
      int page,
      int size,
      long totalElements,
      int totalPages,
      List<ProductDto> content
  ) {
  }

  public record HttpContractResult(
      String method,
      String path,
      int statusCode,
      String contentType,
      Object responseBody
  ) {
  }

  public record SearchCriteria(
      String type,
      String name,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      Long categoryId
  ) {
  }

  public record ProductListResponse(
      List<ProductDto> products,
      PaginationResult pagination
  ) {
  }

  public record ProductDetailResponse(
      ProductDto product,
      CategoryDto category
  ) {
  }

  public record CategoryWithProductsResponse(
      CategoryDto category,
      List<ProductDto> products
  ) {
  }
}
