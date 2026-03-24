package com.example.servlets.config;

import com.example.servlets.model.ApiModels;
import com.example.servlets.model.Category;
import com.example.servlets.model.Product;
import com.example.servlets.model.Tag;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Initialize demo data for servlet application.
 */
@WebListener
public class DemoDataInitializer implements ServletContextListener {

  public static final String PRODUCTS_KEY = "demoProducts";
  public static final String CATEGORIES_KEY = "demoCategories";
  public static final String TAGS_KEY = "demoTags";
  public static final String PRODUCT_ID_KEY = "productIdGenerator";

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();

    // Initialize ID generator
    AtomicLong productIdGenerator = new AtomicLong(1);
    context.setAttribute(PRODUCT_ID_KEY, productIdGenerator);

    // Initialize categories
    List<Category> categories = new ArrayList<>();
    categories.add(new Category(1L, "DEVICE", "Электронные устройства"));
    categories.add(new Category(2L, "BOOK", "Книги"));
    categories.add(new Category(3L, "CLOTHING", "Одежда"));
    categories.add(new Category(4L, "FOOD", "Продукты питания"));
    context.setAttribute(CATEGORIES_KEY, categories);

    // Initialize tags
    List<Tag> tags = new ArrayList<>();
    Tag saleTag = new Tag(1L, "sale");
    Tag newTag = new Tag(2L, "new");
    Tag popularTag = new Tag(3L, "popular");
    Tag limitedTag = new Tag(4L, "limited");
    tags.add(saleTag);
    tags.add(newTag);
    tags.add(popularTag);
    tags.add(limitedTag);
    context.setAttribute(TAGS_KEY, tags);

    // Initialize products
    Map<Long, Product> products = new ConcurrentHashMap<>();

    Set<Tag> smartphoneTags = new HashSet<>();
    smartphoneTags.add(saleTag);
    smartphoneTags.add(newTag);
    products.put(1L, new Product(
        1L, "Smartphone X", new BigDecimal("999.99"), "DEVICE",
        "Latest smartphone with 5G support", smartphoneTags
    ));

    Set<Tag> laptopTags = new HashSet<>();
    laptopTags.add(newTag);
    products.put(2L, new Product(
        2L, "Laptop Pro", new BigDecimal("1499.99"), "DEVICE",
        "Professional laptop for developers", laptopTags
    ));

    products.put(3L, new Product(
        3L, "Clean Code", new BigDecimal("29.99"), "BOOK",
        "A Handbook of Agile Software Craftsmanship", null
    ));

    Set<Tag> tshirtTags = new HashSet<>();
    tshirtTags.add(saleTag);
    products.put(4L, new Product(
        4L, "T-Shirt", new BigDecimal("19.99"), "CLOTHING",
        "Cotton t-shirt with logo", tshirtTags
    ));

    Set<Tag> coffeeTags = new HashSet<>();
    coffeeTags.add(popularTag);
    products.put(5L, new Product(
        5L, "Organic Coffee", new BigDecimal("12.99"), "FOOD",
        "Premium organic coffee beans", coffeeTags
    ));

    context.setAttribute(PRODUCTS_KEY, products);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // Cleanup if needed
  }
}
