package com.example.springrest.repository;

import com.example.springrest.entity.Product;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @EntityGraph(attributePaths = {"category"})
  List<Product> findByType(String type);

  @EntityGraph(attributePaths = {"category"})
  Page<Product> findByType(String type, Pageable pageable);

  @EntityGraph(attributePaths = {"category"})
  List<Product> findByTypeOrderByName(String type);

  @EntityGraph(attributePaths = {"category"})
  Page<Product> findByPriceGreaterThan(BigDecimal price, Pageable pageable);

  @EntityGraph(attributePaths = {"category"})
  Page<Product> findByTypeAndPriceGreaterThan(String type, BigDecimal price, Pageable pageable);

  @EntityGraph(attributePaths = {"category"})
  List<Product> findByCategoryId(Long categoryId);

  @EntityGraph(attributePaths = {"category"})
  @Query("select p from Product p where p.type = :type and p.price > :price order by p.name")
  List<Product> findExpensiveByType(@Param("type") String type, @Param("price") BigDecimal price);

  @EntityGraph(attributePaths = {"category"})
  Optional<Product> findByName(String name);

  @Query("select p from Product p left join fetch p.category where p.id = :id")
  Optional<Product> findWithCategoryById(@Param("id") Long id);

  @EntityGraph(attributePaths = {"category"})
  @Override
  Page<Product> findAll(Pageable pageable);

  @EntityGraph(attributePaths = {"category"})
  @Query("select p from Product p left join fetch p.category")
  List<Product> findAllWithCategory();

  boolean existsByName(String name);
}
