package com.example.springdatajdbc.repository;

import com.example.springdatajdbc.entity.Category;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends ListCrudRepository<Category, Long> {

  Optional<Category> findByTitle(String title);

  List<Category> findByTitleContainingIgnoreCase(String titlePart);

  @Query("""
      SELECT DISTINCT c.*
      FROM "categories" c
      JOIN "category_product" cp ON cp."category_id" = c."ID"
      WHERE cp."PRICE" >= :minPrice
      """)
  List<Category> findCategoriesWithProductsAbovePrice(@Param("minPrice") BigDecimal minPrice);
}