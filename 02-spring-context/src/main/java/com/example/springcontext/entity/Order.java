package com.example.springcontext.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Модель заказа для демонстрации работы Spring IoC контейнера.
 */
public class Order {

    private final String id;
    private String orderNumber;
    private BigDecimal total;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private Customer customer;
    private final List<OrderItem> items = new ArrayList<>();

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public Order(String id, String orderNumber, BigDecimal total) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.total = total;
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public Order(String orderNumber, BigDecimal total) {
        this.id = UUID.randomUUID().toString();
        this.orderNumber = orderNumber;
        this.total = total;
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
        recalculateTotal();
    }

    public void recalculateTotal() {
        this.total = items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", total=" + total +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", customer=" + (customer != null ? customer.getName() : "null") +
                '}';
    }
}
