package com.example.springdatajpa.repository;

import com.example.springdatajpa.entity.Product;
import com.example.springdatajpa.repository.projection.ProductView;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  List<Product> findByTypeOrderByName(String type);

  List<Product> findByCategory_Title(String title);

  List<Product> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

  Page<Product> findByPriceGreaterThan(BigDecimal price, Pageable pageable);

  Slice<Product> findByType(String type, Pageable pageable);

  List<ProductView> findProjectedByTypeOrderByName(String type);
}