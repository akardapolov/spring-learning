package com.example.springdatajdbc.model;

import java.math.BigDecimal;
import java.util.List;

public final class ApiModels {

  private ApiModels() {
  }

  public record ApiResponse<T>(boolean success, T data) {
    public static <T> ApiResponse<T> ok(T data) {
      return new ApiResponse<>(true, data);
    }
  }

  public record ApiErrorResponse(boolean success, String errorCode, String message) {
    public static ApiErrorResponse of(String errorCode, String message) {
      return new ApiErrorResponse(false, errorCode, message);
    }
  }

  public record DifferenceItem(String topic, String jdbc, String jpa) {
  }

  public record DifferencesResult(List<DifferenceItem> items) {
  }

  public record AggregateSummary(String aggregateRoot, String description, List<String> children) {
  }

  public record DddResult(List<AggregateSummary> aggregates) {
  }

  public record ProductDto(Long id, String name, String type, BigDecimal price,
                            String manufacturer, String sku, Long tagId, Long version) {
  }

  public record CategoryDto(Long id, String title, List<ProductDto> products) {
  }

  public record CategoryCreateRequest(String title) {
  }

  public record CategoryUpdateRequest(String title) {
  }

  public record ProductCreateRequest(String name, String type, BigDecimal price,
                                      String manufacturer, String sku, Long tagId) {
  }

  public record ProductUpdateRequest(String name, String type, BigDecimal price,
                                      String manufacturer, String sku, Long tagId) {
  }

  public record CustomerOrderDto(Long id, String description) {
  }

  public record CustomerDto(Long id, String name, String email, List<CustomerOrderDto> orders) {
  }

  public record CustomerCreateRequest(String name, String email) {
  }

  public record OrderCreateRequest(String description) {
  }

  public record CustomQueryResult(int count, List<String> categoryTitles) {
  }

  public record LifecycleEventResult(List<String> events) {
  }

  public record GlossaryItem(String term, String definition) {
  }

  public record GlossaryResult(List<GlossaryItem> items) {
  }

  public record EndpointItem(String method, String path, String description) {
  }

  public record EndpointListResult(List<EndpointItem> endpoints) {
  }
}
