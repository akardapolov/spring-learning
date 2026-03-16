package com.example.hibernate.controller;

import com.example.hibernate.model.ApiModels.ApiResponse;
import com.example.hibernate.model.ApiModels.BatchInsertResult;
import com.example.hibernate.model.ApiModels.CachingResult;
import com.example.hibernate.model.ApiModels.FlushClearResult;
import com.example.hibernate.model.ApiModels.LazyCategoryResult;
import com.example.hibernate.model.ApiModels.LazyExceptionResult;
import com.example.hibernate.model.ApiModels.LazyProductResult;
import com.example.hibernate.model.ApiModels.LifecycleResult;
import com.example.hibernate.model.ApiModels.NPlusOneResult;
import com.example.hibernate.model.ApiModels.OptimisticLockingResult;
import com.example.hibernate.model.ApiModels.ProjectionDemoResult;
import com.example.hibernate.model.ApiModels.QueryCacheResult;
import com.example.hibernate.model.ApiModels.QueryComparisonResult;
import com.example.hibernate.model.ApiModels.ReadOnlyInspectionResult;
import com.example.hibernate.model.ApiModels.RelationshipSummaryResult;
import com.example.hibernate.model.ApiModels.SpecificationDemoResult;
import com.example.hibernate.model.ApiModels.SpringDataEntityGraphResult;
import com.example.hibernate.model.ApiModels.SpringDataPaginationResult;
import com.example.hibernate.model.ApiModels.SpringDataQueryMethodsResult;
import com.example.hibernate.model.ApiModels.StatisticsSnapshot;
import com.example.hibernate.service.AdvancedSpringDataService;
import com.example.hibernate.service.BatchInsertService;
import com.example.hibernate.service.CachingDemoService;
import com.example.hibernate.service.EntityLifecycleDemoService;
import com.example.hibernate.service.FetchingDemoService;
import com.example.hibernate.service.FlushClearDemoService;
import com.example.hibernate.service.LazyExceptionDemoService;
import com.example.hibernate.service.NPlusOneDemoService;
import com.example.hibernate.service.OptimisticLockingDemoService;
import com.example.hibernate.service.QueryComparisonService;
import com.example.hibernate.service.ReadOnlyDemoService;
import com.example.hibernate.service.RelationshipDemoService;
import com.example.hibernate.service.StatisticsService;
import com.example.hibernate.springdata.SpringDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/demo")
@RestController
public class DemoController {

  private final EntityLifecycleDemoService lifecycleService;
  private final RelationshipDemoService relationshipService;
  private final FetchingDemoService fetchingService;
  private final NPlusOneDemoService nPlusOneDemoService;
  private final CachingDemoService cachingDemoService;
  private final StatisticsService statisticsService;
  private final SpringDataService springDataService;
  private final OptimisticLockingDemoService optimisticLockingDemoService;
  private final QueryComparisonService queryComparisonService;
  private final BatchInsertService batchInsertService;
  private final FlushClearDemoService flushClearDemoService;
  private final LazyExceptionDemoService lazyExceptionDemoService;
  private final ReadOnlyDemoService readOnlyDemoService;
  private final AdvancedSpringDataService advancedSpringDataService;

  public DemoController(EntityLifecycleDemoService lifecycleService,
                        RelationshipDemoService relationshipService,
                        FetchingDemoService fetchingService,
                        NPlusOneDemoService nPlusOneDemoService,
                        CachingDemoService cachingDemoService,
                        StatisticsService statisticsService,
                        SpringDataService springDataService,
                        OptimisticLockingDemoService optimisticLockingDemoService,
                        QueryComparisonService queryComparisonService,
                        BatchInsertService batchInsertService,
                        FlushClearDemoService flushClearDemoService,
                        LazyExceptionDemoService lazyExceptionDemoService,
                        ReadOnlyDemoService readOnlyDemoService,
                        AdvancedSpringDataService advancedSpringDataService) {
    this.lifecycleService = lifecycleService;
    this.relationshipService = relationshipService;
    this.fetchingService = fetchingService;
    this.nPlusOneDemoService = nPlusOneDemoService;
    this.cachingDemoService = cachingDemoService;
    this.statisticsService = statisticsService;
    this.springDataService = springDataService;
    this.optimisticLockingDemoService = optimisticLockingDemoService;
    this.queryComparisonService = queryComparisonService;
    this.batchInsertService = batchInsertService;
    this.flushClearDemoService = flushClearDemoService;
    this.lazyExceptionDemoService = lazyExceptionDemoService;
    this.readOnlyDemoService = readOnlyDemoService;
    this.advancedSpringDataService = advancedSpringDataService;
  }

  @GetMapping("/lifecycle")
  public ApiResponse<LifecycleResult> lifecycle() {
    return ApiResponse.ok(lifecycleService.demonstrateLifecycle());
  }

  @GetMapping("/relationships")
  public ApiResponse<RelationshipSummaryResult> relationships() {
    return ApiResponse.ok(relationshipService.summarizeRelationships());
  }

  @GetMapping("/fetching/category/{id}")
  public ApiResponse<LazyCategoryResult> categoryFetching(@PathVariable Long id) {
    return ApiResponse.ok(fetchingService.lazyCategory(id));
  }

  @GetMapping("/fetching/product/{id}")
  public ApiResponse<LazyProductResult> productFetching(@PathVariable Long id) {
    return ApiResponse.ok(fetchingService.lazyProduct(id));
  }

  @GetMapping("/n-plus-one/lazy")
  public ApiResponse<NPlusOneResult> nPlusOneLazy() {
    return ApiResponse.ok(nPlusOneDemoService.lazyScenario());
  }

  @GetMapping("/n-plus-one/join-fetch")
  public ApiResponse<NPlusOneResult> nPlusOneJoinFetch() {
    return ApiResponse.ok(nPlusOneDemoService.joinFetchScenario());
  }

  @GetMapping("/n-plus-one/entity-graph")
  public ApiResponse<NPlusOneResult> nPlusOneEntityGraph() {
    return ApiResponse.ok(nPlusOneDemoService.entityGraphScenario());
  }

  @GetMapping("/n-plus-one/subselect")
  public ApiResponse<NPlusOneResult> nPlusOneSubselect() {
    return ApiResponse.ok(nPlusOneDemoService.subselectScenario());
  }

  @GetMapping("/caching/l1/{id}")
  public ApiResponse<CachingResult> l1(@PathVariable Long id) {
    return ApiResponse.ok(cachingDemoService.firstLevelCache(id));
  }

  @GetMapping("/caching/query/{type}")
  public ApiResponse<QueryCacheResult> queryCache(@PathVariable String type) {
    return ApiResponse.ok(cachingDemoService.queryCache(type));
  }

  @GetMapping("/caching/reset-stats")
  public ApiResponse<Boolean> resetStats() {
    statisticsService.reset();
    return ApiResponse.ok(true);
  }

  @GetMapping("/stats")
  public ApiResponse<StatisticsSnapshot> stats() {
    return ApiResponse.ok(statisticsService.snapshot());
  }

  @GetMapping("/spring-data/query-methods")
  public ApiResponse<SpringDataQueryMethodsResult> springDataQueryMethods() {
    return ApiResponse.ok(springDataService.queryMethodsDemo());
  }

  @GetMapping("/spring-data/entity-graph")
  public ApiResponse<SpringDataEntityGraphResult> springDataEntityGraph() {
    return ApiResponse.ok(springDataService.queryAndEntityGraphDemo());
  }

  @GetMapping("/spring-data/pagination")
  public ApiResponse<SpringDataPaginationResult> springDataPagination() {
    return ApiResponse.ok(springDataService.paginationDemo());
  }

  @GetMapping("/optimistic-locking/{id}")
  public ApiResponse<OptimisticLockingResult> optimisticLocking(@PathVariable Long id) {
    return ApiResponse.ok(optimisticLockingDemoService.demonstrate(id));
  }

  @GetMapping("/advanced/query-comparison")
  public ApiResponse<QueryComparisonResult> queryComparison() {
    return ApiResponse.ok(queryComparisonService.compare());
  }

  @GetMapping("/advanced/batch-insert/{count}")
  public ApiResponse<BatchInsertResult> batchInsert(@PathVariable int count) {
    return ApiResponse.ok(batchInsertService.insert(count));
  }

  @GetMapping("/advanced/flush-clear")
  public ApiResponse<FlushClearResult> flushClear() {
    return ApiResponse.ok(flushClearDemoService.demonstrate());
  }

  @GetMapping("/advanced/lazy-exception/{id}")
  public ApiResponse<LazyExceptionResult> lazyException(@PathVariable Long id) {
    return ApiResponse.ok(lazyExceptionDemoService.demonstrate(id));
  }

  @GetMapping("/advanced/read-only/{id}")
  public ApiResponse<ReadOnlyInspectionResult> readOnly(@PathVariable Long id) {
    return ApiResponse.ok(readOnlyDemoService.inspect(id));
  }

  @GetMapping("/advanced/specification")
  public ApiResponse<SpecificationDemoResult> specification() {
    return ApiResponse.ok(advancedSpringDataService.specificationDemo());
  }

  @GetMapping("/advanced/projections")
  public ApiResponse<ProjectionDemoResult> projections() {
    return ApiResponse.ok(advancedSpringDataService.projectionDemo());
  }
}