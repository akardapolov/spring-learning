package com.example.hibernate.springdata;

import com.example.hibernate.entity.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategorySpringDataRepository extends JpaRepository<Category, Long> {

  @Query("select distinct c from Category c join fetch c.products where c.title = :title")
  Optional<Category> findDetailedByTitle(String title);

  @EntityGraph(attributePaths = "products")
  @Query("select c from Category c")
  List<Category> findAllWithProducts();
}