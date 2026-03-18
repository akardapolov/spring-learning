package com.example.springdatajpa.service;

import com.example.springdatajpa.model.ApiModels.ProductSummaryDto;
import com.example.springdatajpa.model.ApiModels.TypeStatsDto;
import com.example.springdatajpa.entity.Product;
import com.example.springdatajpa.repository.QueryProductRepository;
import com.example.springdatajpa.repository.projection.ProductFullView;
import jakarta.persistence.Tuple;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComplexQueryDemoService {

  private final QueryProductRepository queryRepo;

  public Map<String, Object> demoJoinFetch(String type) {
    Map<String, Object> result = new LinkedHashMap<>();
    result.put("approach", "JOIN FETCH");
    result.put("description", "Entity с загруженными связями — один SQL запрос");

    List<Product> products = queryRepo.findWithCategoryAndTagsByType(type);

    List<Map<String, Object>> items = products.stream().map(p -> {
      Map<String, Object> item = new LinkedHashMap<>();
      item.put("id", p.getId());
      item.put("name", p.getName());
      item.put("price", p.getPrice());
      item.put("category", Map.of(
          "id", p.getCategory().getId(),
          "title", p.getCategory().getTitle()
      ));
      item.put("tags", p.getTags().stream()
          .map(t -> Map.of("id", t.getId(), "name", t.getName()))
          .toList());
      item.put("tagCount", p.getTags().size());
      return item;
    }).toList();

    result.put("products", items);
    result.put("count", items.size());
    return result;
  }

  public Map<String, Object> demoDtoProjection(String type) {
    List<ProductSummaryDto> summaries = queryRepo.findSummariesByType(type);

    Map<String, Object> result = new LinkedHashMap<>();
    result.put("approach", "select new DTO()");
    result.put("description", "Плоский DTO из JPQL — поля из разных таблиц в одном объекте");
    result.put("products", summaries);
    result.put("count", summaries.size());

    Map<String, List<ProductSummaryDto>> byCategory = summaries.stream()
        .collect(Collectors.groupingBy(ProductSummaryDto::categoryTitle));
    result.put("groupedByCategory", byCategory);

    return result;
  }

  public Map<String, Object> demoNestedProjection(String type) {
    List<ProductFullView> views = queryRepo.findProductFullViewByType(type);

    Map<String, Object> result = new LinkedHashMap<>();
    result.put("approach", "Nested interface projection");
    result.put("description", "Вложенные проекции — Category и Tags как вложенные объекты");

    List<Map<String, Object>> items = views.stream().map(v -> {
      Map<String, Object> item = new LinkedHashMap<>();
      item.put("id", v.getId());
      item.put("name", v.getName());
      item.put("price", v.getPrice());
      item.put("type", v.getType());
      if (v.getCategory() != null) {
        item.put("category", Map.of(
            "id", v.getCategory().getId(),
            "title", v.getCategory().getTitle()
        ));
      }
      item.put("tags", v.getTags().stream()
          .map(t -> Map.of("id", t.getId(), "name", t.getName()))
          .toList());
      return item;
    }).toList();

    result.put("products", items);
    result.put("count", items.size());
    return result;
  }

  public Map<String, Object> demoTupleAggregation() {
    List<Tuple> tuples = queryRepo.findStatsByTypeTuple();

    List<TypeStatsDto> stats = tuples.stream().map(t -> new TypeStatsDto(
        t.get("type", String.class),
        t.get("productCount", Long.class),
        BigDecimal.valueOf(t.get("avgPrice", Double.class))
            .setScale(2, RoundingMode.HALF_UP),
        t.get("minPrice", BigDecimal.class),
        t.get("maxPrice", BigDecimal.class)
    )).toList();

    Map<String, Object> result = new LinkedHashMap<>();
    result.put("approach", "Tuple aggregation");
    result.put("description", "GROUP BY + COUNT/AVG/MIN/MAX → Tuple → DTO");
    result.put("statsByType", stats);

    Map<String, TypeStatsDto> statsMap = stats.stream()
        .collect(Collectors.toMap(TypeStatsDto::type, s -> s));
    result.put("statsMap", statsMap);

    return result;
  }

  public Map<String, Object> demoNativeComplex(String type) {
    List<Object[]> rows = queryRepo.findProductReportNative(type);

    List<Map<String, Object>> items = rows.stream().map(row -> {
      Map<String, Object> item = new LinkedHashMap<>();
      item.put("id", row[0]);
      item.put("name", row[1]);
      item.put("price", row[2]);
      item.put("type", row[3]);
      item.put("categoryTitle", row[4]);
      item.put("tagCount", row[5]);
      return item;
    }).toList();

    Map<String, Object> result = new LinkedHashMap<>();
    result.put("approach", "Native SQL + manual mapping");
    result.put("description", "Чистый SQL с подзапросами → Object[] → Map");
    result.put("products", items);
    result.put("count", items.size());
    return result;
  }

  public Map<String, Object> demoAssembledResponse() {
    Map<String, Object> response = new LinkedHashMap<>();
    response.put("approach", "Service-layer assembly");
    response.put("description", "Несколько простых запросов → сборка в сервисе");

    List<Tuple> tuples = queryRepo.findStatsByTypeTuple();
    Map<String, TypeStatsDto> stats = tuples.stream()
        .map(t -> new TypeStatsDto(
            t.get("type", String.class),
            t.get("productCount", Long.class),
            BigDecimal.valueOf(t.get("avgPrice", Double.class))
                .setScale(2, RoundingMode.HALF_UP),
            t.get("minPrice", BigDecimal.class),
            t.get("maxPrice", BigDecimal.class)))
        .collect(Collectors.toMap(TypeStatsDto::type, s -> s));

    Map<String, Object> byType = new LinkedHashMap<>();
    for (String type : stats.keySet()) {
      List<ProductSummaryDto> products = queryRepo.findSummariesByType(type);

      Map<String, Object> section = new LinkedHashMap<>();
      section.put("stats", stats.get(type));
      section.put("products", products);
      section.put("byCategory", products.stream()
          .collect(Collectors.groupingBy(ProductSummaryDto::categoryTitle)));

      byType.put(type, section);
    }

    response.put("types", byType);
    response.put("totalTypes", stats.size());
    return response;
  }
}