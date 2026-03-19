package com.example.springdatajdbc.repository;

import com.example.springdatajdbc.entity.Tag;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface TagRepository extends ListCrudRepository<Tag, Long> {

  Optional<Tag> findByName(String name);
}
