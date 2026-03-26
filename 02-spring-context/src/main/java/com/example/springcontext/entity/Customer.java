package com.example.springcontext.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Модель клиента для демонстрации работы Spring IoC контейнера.
 */
public class Customer {

    private final String id;
    private String name;
    private String email;
    private BigDecimal balance;
    private final List<Order> orders = new ArrayList<>();

    public Customer() {
        this.id = UUID.randomUUID().toString();
    }

    public Customer(String id, String name, String email, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public Customer(String name, String email, BigDecimal balance) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setCustomer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}
