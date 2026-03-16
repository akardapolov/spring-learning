package com.example.hibernate.springdata;

import com.example.hibernate.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductSpringDataRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  List<Product> findByTypeOrderByName(String type);

  List<Product> findByCategory_Title(String title);

  Page<Product> findByPriceGreaterThan(BigDecimal price, Pageable pageable);

  List<ProductView> findProjectedByTypeOrderByName(String type);
}