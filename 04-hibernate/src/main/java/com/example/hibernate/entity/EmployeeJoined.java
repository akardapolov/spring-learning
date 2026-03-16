package com.example.hibernate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees_joined")
public class EmployeeJoined extends PersonJoined {

  private String department;

  public EmployeeJoined() {
  }

  public EmployeeJoined(String name, String department) {
    super(name);
    this.department = department;
  }

  public String getDepartment() {
    return department;
  }
}