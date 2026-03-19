package com.example.springdatajdbc.repository;

import com.example.springdatajdbc.entity.Customer;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface CustomerRepository extends ListCrudRepository<Customer, Long> {

  Optional<Customer> findByEmail(String email);
}
