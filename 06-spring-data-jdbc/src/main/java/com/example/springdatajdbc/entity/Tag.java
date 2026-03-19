package com.example.springdatajdbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tags")
public class Tag {

  @Id
  private Long id;

  private String name;

  public Tag() {
  }

  public Tag(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
