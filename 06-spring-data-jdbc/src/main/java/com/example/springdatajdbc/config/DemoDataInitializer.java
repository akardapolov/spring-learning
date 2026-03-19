package com.example.springdatajdbc.config;

import com.example.springdatajdbc.entity.Category;
import com.example.springdatajdbc.entity.CategoryProduct;
import com.example.springdatajdbc.entity.Customer;
import com.example.springdatajdbc.entity.CustomerOrder;
import com.example.springdatajdbc.entity.ProductDetails;
import com.example.springdatajdbc.entity.Tag;
import com.example.springdatajdbc.repository.CategoryRepository;
import com.example.springdatajdbc.repository.CustomerRepository;
import com.example.springdatajdbc.repository.TagRepository;
import java.math.BigDecimal;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

@Component
public class DemoDataInitializer implements ApplicationRunner {

  private final CategoryRepository categoryRepository;
  private final CustomerRepository customerRepository;
  private final TagRepository tagRepository;

  public DemoDataInitializer(CategoryRepository categoryRepository,
                             CustomerRepository customerRepository,
                             TagRepository tagRepository) {
    this.categoryRepository = categoryRepository;
    this.customerRepository = customerRepository;
    this.tagRepository = tagRepository;
  }

  @Override
  public void run(ApplicationArguments args) {
    if (categoryRepository.count() > 0) {
      return;
    }

    Tag popular = tagRepository.save(new Tag("popular"));
    Tag sale = tagRepository.save(new Tag("sale"));
    Tag premium = tagRepository.save(new Tag("premium"));

    Category electronics = categoryRepository.save(new Category("Electronics"));
    electronics.addProduct(new CategoryProduct("Phone", "DEVICE", BigDecimal.valueOf(999),
                                               new ProductDetails("Acme", "SKU-PHONE-1"), AggregateReference.to(popular.getId())));
    electronics.addProduct(new CategoryProduct("Laptop", "DEVICE", BigDecimal.valueOf(1999),
                                               new ProductDetails("Acme", "SKU-LAPTOP-1"), AggregateReference.to(premium.getId())));
    categoryRepository.save(electronics);

    Category books = categoryRepository.save(new Category("Books"));
    books.addProduct(new CategoryProduct("Novel", "BOOK", BigDecimal.valueOf(19),
                                         new ProductDetails("Books Inc", "SKU-NOVEL-1"), AggregateReference.to(popular.getId())));
    books.addProduct(new CategoryProduct("Cookbook", "BOOK", BigDecimal.valueOf(29),
                                         new ProductDetails("Books Inc", "SKU-COOK-1"), AggregateReference.to(sale.getId())));
    categoryRepository.save(books);

    Category home = categoryRepository.save(new Category("Home"));
    home.addProduct(new CategoryProduct("Vacuum Cleaner", "HOME", BigDecimal.valueOf(299),
                                        new ProductDetails("HomeTech", "SKU-VAC-1"), AggregateReference.to(sale.getId())));
    home.addProduct(new CategoryProduct("Lamp", "HOME", BigDecimal.valueOf(49),
                                        new ProductDetails("HomeTech", "SKU-LAMP-1"), AggregateReference.to(popular.getId())));
    categoryRepository.save(home);

    Customer customer = new Customer("Ivan Petrov", "ivan@example.com");
    customer.addOrder(new CustomerOrder("First order"));
    customer.addOrder(new CustomerOrder("Second order"));
    customerRepository.save(customer);
  }
}