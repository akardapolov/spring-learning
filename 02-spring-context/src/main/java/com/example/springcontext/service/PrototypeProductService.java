package com.example.springcontext.service;

import com.example.springcontext.entity.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Сервис с prototype scope для демонстрации разницы между singleton и prototype.
 */
@Service
@Scope("prototype")
public class PrototypeProductService {

    private final Product currentProduct;

    public PrototypeProductService() {
        this.currentProduct = new Product("Prototype Product", "PROTOTYPE", BigDecimal.valueOf(1.00));
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }
}
