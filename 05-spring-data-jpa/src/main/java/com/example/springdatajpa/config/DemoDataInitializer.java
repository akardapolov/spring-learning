package com.example.springdatajpa.config;

import com.example.springdatajpa.entity.Category;
import com.example.springdatajpa.entity.Customer;
import com.example.springdatajpa.entity.CustomerOrder;
import com.example.springdatajpa.entity.Product;
import com.example.springdatajpa.entity.ProductDetails;
import com.example.springdatajpa.entity.Tag;
import com.example.springdatajpa.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DemoDataInitializer implements ApplicationRunner {

  private final ProductRepository productRepository;
  private final EntityManager entityManager;

  public DemoDataInitializer(ProductRepository productRepository, EntityManager entityManager) {
    this.productRepository = productRepository;
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public void run(ApplicationArguments args) {
    if (productRepository.count() > 0) {
      return;
    }

    Category electronics = new Category("Electronics");
    Category books = new Category("Books");
    Category home = new Category("Home");

    Product phone = new Product("Phone", "DEVICE", BigDecimal.valueOf(999),
                                new ProductDetails("Acme", "SKU-PHONE-1"));
    Product laptop = new Product("Laptop", "DEVICE", BigDecimal.valueOf(1999),
                                 new ProductDetails("Acme", "SKU-LAPTOP-1"));
    Product novel = new Product("Novel", "BOOK", BigDecimal.valueOf(19),
                                new ProductDetails("Books Inc", "SKU-NOVEL-1"));
    Product cookbook = new Product("Cookbook", "BOOK", BigDecimal.valueOf(29),
                                   new ProductDetails("Books Inc", "SKU-COOK-1"));
    Product vacuum = new Product("Vacuum Cleaner", "HOME", BigDecimal.valueOf(299),
                                 new ProductDetails("HomeTech", "SKU-VAC-1"));
    Product lamp = new Product("Lamp", "HOME", BigDecimal.valueOf(49),
                               new ProductDetails("HomeTech", "SKU-LAMP-1"));

    electronics.addProduct(phone);
    electronics.addProduct(laptop);
    books.addProduct(novel);
    books.addProduct(cookbook);
    home.addProduct(vacuum);
    home.addProduct(lamp);

    Tag popular = new Tag("popular");
    Tag sale = new Tag("sale");
    Tag premium = new Tag("premium");

    popular.addProduct(phone);
    premium.addProduct(laptop);
    sale.addProduct(vacuum);
    popular.addProduct(novel);

    Customer customer = new Customer("Ivan Petrov", "ivan@example.com");
    customer.addOrder(new CustomerOrder("First order"));
    customer.addOrder(new CustomerOrder("Second order"));

    entityManager.persist(electronics);
    entityManager.persist(books);
    entityManager.persist(home);
    entityManager.persist(popular);
    entityManager.persist(sale);
    entityManager.persist(premium);
    entityManager.persist(customer);

    entityManager.flush();
    entityManager.clear();
  }
}