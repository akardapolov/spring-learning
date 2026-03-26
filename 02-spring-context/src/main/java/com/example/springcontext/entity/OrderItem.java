package com.example.springcontext.entity;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Элемент заказа для демонстрации работы Spring IoC контейнера.
 */
public class OrderItem {

    private final String id;
    private String productName;
    private int quantity;
    private BigDecimal price;

    public OrderItem() {
        this.id = UUID.randomUUID().toString();
    }

    public OrderItem(String id, String productName, int quantity, BigDecimal price) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItem(String productName, int quantity, BigDecimal price) {
        this.id = UUID.randomUUID().toString();
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
