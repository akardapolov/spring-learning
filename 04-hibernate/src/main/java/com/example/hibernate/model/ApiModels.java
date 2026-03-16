package com.example.hibernate.model;

import java.math.BigDecimal;

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

  public record OptimisticLockingResult(
      Long productId,
      Long initialVersionTx1,
      Long initialVersionTx2,
      boolean tx1Committed,
      boolean tx2Committed,
      boolean optimisticLockTriggered,
      String error
  ) {
  }

  public record LazyExceptionResult(
      Long categoryId,
      String categoryTitle,
      Integer productsSize,
      boolean lazyInitializationException,
      String exceptionType
  ) {
  }

  public record QueryComparisonResult(
      int nativeCount,
      int jpqlCount,
      int criteriaCount
  ) {
  }

  public record SpecificationDemoResult(
      int matchedCount
  ) {
  }

  public record ProjectionDemoResult(
      int projectionCount,
      String firstProjectionName
  ) {
  }

  public record BatchInsertResult(
      int inserted,
      int batchSize
  ) {
  }

  public record FlushClearResult(
      Long idAfterPersist,
      boolean containsAfterFlush,
      boolean containsAfterClear,
      Long reloadedId,
      boolean sameJavaInstance
  ) {
  }

  public record StatisticsSnapshot(
      long entityLoadCount,
      long entityFetchCount,
      long queryExecutionCount,
      long prepareStatementCount,
      long secondLevelCacheHitCount,
      long secondLevelCacheMissCount,
      long secondLevelCachePutCount,
      long queryCacheHitCount,
      long queryCacheMissCount,
      long queryCachePutCount,
      long flushCount,
      long optimisticFailureCount
  ) {
  }

  public record NPlusOneResult(
      String scenario,
      int categories,
      int products
  ) {
  }

  public record CachingResult(
      boolean sameInstanceWithinTransaction,
      String productName
  ) {
  }

  public record QueryCacheResult(
      int firstCallSize,
      int secondCallSize,
      String type
  ) {
  }

  public record ReadOnlyInspectionResult(
      Long id,
      String name,
      BigDecimal price,
      String categoryTitle
  ) {
  }

  public record RelationshipSummaryResult(
      int categories,
      int tags,
      int customers
  ) {
  }

  public record LifecycleResult(
      boolean transientHasId,
      boolean managedAfterPersistHasId,
      Long detachedId,
      Long mergedManagedId,
      BigDecimal mergedPrice
  ) {
  }

  public record LazyCategoryResult(
      Long categoryId,
      String categoryTitle,
      int productsCount
  ) {
  }

  public record LazyProductResult(
      Long productId,
      String productName,
      String categoryTitle
  ) {
  }

  public record SpringDataQueryMethodsResult(
      int devicesCount,
      int electronicsProductsCount
  ) {
  }

  public record SpringDataEntityGraphResult(
      int detailedCategoryProducts,
      int allCategoriesLoaded
  ) {
  }

  public record SpringDataPaginationResult(
      int pageSize,
      long totalElements,
      int contentSize
  ) {
  }
}