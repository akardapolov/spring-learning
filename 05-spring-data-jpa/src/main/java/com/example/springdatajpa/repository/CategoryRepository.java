package com.example.springdatajpa.repository;

import com.example.springdatajpa.entity.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("select distinct c from Category c join fetch c.products where c.title = :title")
  Optional<Category> findDetailedByTitle(String title);

  @EntityGraph(value = "category-with-products")
  @Query("select distinct c from Category c")
  List<Category> findAllWithProducts();
}