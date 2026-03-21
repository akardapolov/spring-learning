package com.example.springrest.repository;

import com.example.springrest.entity.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findByTitle(String title);

  Optional<Category> findByTitleIgnoreCase(String title);

  @Query("select c from Category c left join fetch c.products where c.id = :id")
  Optional<Category> findWithProductsById(@Param("id") Long id);

  boolean existsByTitle(String title);
}
