package com.example.springdatajpa.repository;

import com.example.springdatajpa.model.ApiModels.ProductSummaryDto;
import com.example.springdatajpa.entity.Product;
import com.example.springdatajpa.repository.projection.ProductFullView;
import jakarta.persistence.Tuple;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QueryProductRepository extends JpaRepository<Product, Long> {

  @Query("select p from Product p where p.type = :type and p.price > :price order by p.name")
  List<Product> findExpensiveByType(String type, BigDecimal price);

  @Query(value = "select * from products where type = :type order by name", nativeQuery = true)
  List<Product> findNativeByType(String type);

  @Query("select distinct p from Product p " +
      "join fetch p.category " +
      "where p.type = :type order by p.name")
  List<Product> findWithCategoryByType(String type);

  @Query("select distinct p from Product p " +
      "join fetch p.category " +
      "left join fetch p.tags " +
      "where p.type = :type order by p.name")
  List<Product> findWithCategoryAndTagsByType(String type);

  @Query("select new com.example.springdatajpa.model.ApiModels$ProductSummaryDto(" +
      "p.id, p.name, p.price, p.category.title) " +
      "from Product p where p.type = :type order by p.name")
  List<ProductSummaryDto> findSummariesByType(String type);

  List<ProductFullView> findProductFullViewByType(String type);

  @Query("select p.type as type, " +
      "count(p) as productCount, " +
      "avg(p.price) as avgPrice, " +
      "min(p.price) as minPrice, " +
      "max(p.price) as maxPrice " +
      "from Product p group by p.type")
  List<Tuple> findStatsByTypeTuple();

  @Query(value =
      "select p.id, p.name, p.price, p.type, " +
          "       c.title as category_title, " +
          "       (select count(*) from product_tags pt where pt.product_id = p.id) as tag_count " +
          "from products p " +
          "join categories c on p.category_id = c.id " +
          "where p.type = :type " +
          "order by p.name",
      nativeQuery = true)
  List<Object[]> findProductReportNative(String type);
}