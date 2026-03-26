package com.example.springcontext.service;

import com.example.springcontext.entity.Customer;
import com.example.springcontext.entity.Order;
import com.example.springcontext.entity.OrderItem;
import com.example.springcontext.entity.OrderStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сервис заказов демонстрирует бизнес-логику на уровне сервиса.
 */
@Service
public class OrderService {

    public Order createOrder(Customer customer, String productName, int quantity, BigDecimal price) {
        Order order = new Order();
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        OrderItem item = new OrderItem(productName, quantity, price);
        order.addItem(item);

        order.setCustomer(customer);
        customer.addOrder(order);

        return order;
    }

    public Order updateOrderStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        return order;
    }

    public BigDecimal calculateDiscount(Order order, BigDecimal discountPercentage) {
        if (discountPercentage.compareTo(BigDecimal.ZERO) < 0
                || discountPercentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
        return order.getTotal()
                .multiply(discountPercentage)
                .divide(BigDecimal.valueOf(100));
    }
}
