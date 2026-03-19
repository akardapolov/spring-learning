package com.example.springdatajdbc.service;

import com.example.springdatajdbc.config.LifecycleEventsCollector;
import com.example.springdatajdbc.entity.Category;
import com.example.springdatajdbc.entity.CategoryProduct;
import com.example.springdatajdbc.entity.Customer;
import com.example.springdatajdbc.entity.CustomerOrder;
import com.example.springdatajdbc.entity.ProductDetails;
import com.example.springdatajdbc.model.ApiModels.AggregateSummary;
import com.example.springdatajdbc.model.ApiModels.CategoryCreateRequest;
import com.example.springdatajdbc.model.ApiModels.CategoryDto;
import com.example.springdatajdbc.model.ApiModels.CategoryUpdateRequest;
import com.example.springdatajdbc.model.ApiModels.CustomQueryResult;
import com.example.springdatajdbc.model.ApiModels.CustomerCreateRequest;
import com.example.springdatajdbc.model.ApiModels.CustomerDto;
import com.example.springdatajdbc.model.ApiModels.CustomerOrderDto;
import com.example.springdatajdbc.model.ApiModels.DddResult;
import com.example.springdatajdbc.model.ApiModels.DifferenceItem;
import com.example.springdatajdbc.model.ApiModels.DifferencesResult;
import com.example.springdatajdbc.model.ApiModels.EndpointItem;
import com.example.springdatajdbc.model.ApiModels.EndpointListResult;
import com.example.springdatajdbc.model.ApiModels.GlossaryItem;
import com.example.springdatajdbc.model.ApiModels.GlossaryResult;
import com.example.springdatajdbc.model.ApiModels.LifecycleEventResult;
import com.example.springdatajdbc.model.ApiModels.OrderCreateRequest;
import com.example.springdatajdbc.model.ApiModels.ProductCreateRequest;
import com.example.springdatajdbc.model.ApiModels.ProductDto;
import com.example.springdatajdbc.model.ApiModels.ProductUpdateRequest;
import com.example.springdatajdbc.repository.CategoryRepository;
import com.example.springdatajdbc.repository.CustomerRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

@Service
public class SpringDataJdbcDemoService {

  private final CategoryRepository categoryRepository;
  private final CustomerRepository customerRepository;
  private final LifecycleEventsCollector lifecycleEventsCollector;

  public SpringDataJdbcDemoService(CategoryRepository categoryRepository,
                                   CustomerRepository customerRepository,
                                   LifecycleEventsCollector lifecycleEventsCollector) {
    this.categoryRepository = categoryRepository;
    this.customerRepository = customerRepository;
    this.lifecycleEventsCollector = lifecycleEventsCollector;
  }

  public DifferencesResult differencesDemo() {
    return new DifferencesResult(List.of(
        new DifferenceItem("Persistence Context",
            "No session or persistence context",
            "EntityManager with persistence context"),
        new DifferenceItem("Lazy Loading",
            "No lazy loading — all data loaded eagerly within aggregate",
            "Lazy loading via proxies"),
        new DifferenceItem("Dirty Checking",
            "No automatic dirty checking — must call save() explicitly",
            "Automatic dirty checking on flush"),
        new DifferenceItem("Relations",
            "Aggregate children + AggregateReference for cross-aggregate links",
            "@OneToMany / @ManyToOne / @ManyToMany annotations"),
        new DifferenceItem("Saving",
            "Entire aggregate saved/deleted atomically",
            "Fine-grained cascade control per relation"),
        new DifferenceItem("Identity",
            "New entity: id == null; existing: id != null",
            "Managed by persistence context identity map"),
        new DifferenceItem("Schema",
            "Explicit SQL schema required (schema.sql)",
            "Hibernate ddl-auto can generate schema")
    ));
  }

  public DddResult dddDemo() {
    return new DddResult(List.of(
        new AggregateSummary("Category",
            "Aggregate root owning CategoryProduct children. Products are saved/deleted together with Category.",
            List.of("CategoryProduct")),
        new AggregateSummary("Customer",
            "Aggregate root owning CustomerOrder children. Orders belong to Customer.",
            List.of("CustomerOrder")),
        new AggregateSummary("Tag",
            "Standalone aggregate. Referenced by CategoryProduct via AggregateReference<Tag, Long>.",
            List.of())
    ));
  }

  public CategoryDto getCategory(Long id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Category not found: " + id));
    return toCategoryDto(category);
  }

  public List<CategoryDto> getAllCategories() {
    return categoryRepository.findAll().stream().map(this::toCategoryDto).toList();
  }

  public CategoryDto createCategory(CategoryCreateRequest request) {
    Category category = new Category(request.title());
    return toCategoryDto(categoryRepository.save(category));
  }

  public CategoryDto updateCategory(Long id, CategoryUpdateRequest request) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Category not found: " + id));
    category.setTitle(request.title());
    return toCategoryDto(categoryRepository.save(category));
  }

  public void deleteCategory(Long id) {
    if (!categoryRepository.existsById(id)) {
      throw new NoSuchElementException("Category not found: " + id);
    }
    categoryRepository.deleteById(id);
  }

  public CategoryDto addProduct(Long categoryId, ProductCreateRequest request) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new NoSuchElementException("Category not found: " + categoryId));
    AggregateReference<com.example.springdatajdbc.entity.Tag, Long> tagRef =
        request.tagId() != null ? AggregateReference.to(request.tagId()) : null;
    category.addProduct(new CategoryProduct(
        request.name(), request.type(), request.price(),
        new ProductDetails(request.manufacturer(), request.sku()), tagRef));
    return toCategoryDto(categoryRepository.save(category));
  }

  public CategoryDto updateProduct(Long categoryId, Long productId, ProductUpdateRequest request) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new NoSuchElementException("Category not found: " + categoryId));
    CategoryProduct product = category.getProducts().stream()
        .filter(p -> p.getId().equals(productId))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Product not found: " + productId));
    product.setName(request.name());
    product.setType(request.type());
    product.setPrice(request.price());
    product.setDetails(new ProductDetails(request.manufacturer(), request.sku()));
    product.setTag(request.tagId() != null ? AggregateReference.to(request.tagId()) : null);
    return toCategoryDto(categoryRepository.save(category));
  }

  public CategoryDto removeProduct(Long categoryId, Long productId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new NoSuchElementException("Category not found: " + categoryId));
    CategoryProduct product = category.getProducts().stream()
        .filter(p -> p.getId().equals(productId))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Product not found: " + productId));
    category.removeProduct(product);
    return toCategoryDto(categoryRepository.save(category));
  }

  public CustomerDto getCustomer(Long id) {
    Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Customer not found: " + id));
    return toCustomerDto(customer);
  }

  public CustomerDto createCustomer(CustomerCreateRequest request) {
    Customer customer = new Customer(request.name(), request.email());
    return toCustomerDto(customerRepository.save(customer));
  }

  public CustomerDto addOrder(Long customerId, OrderCreateRequest request) {
    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new NoSuchElementException("Customer not found: " + customerId));
    customer.addOrder(new CustomerOrder(request.description()));
    return toCustomerDto(customerRepository.save(customer));
  }

  public CustomQueryResult customQueryDemo(String titlePart) {
    List<Category> categories = categoryRepository.findByTitleContainingIgnoreCase(titlePart);
    return new CustomQueryResult(
        categories.size(),
        categories.stream().map(Category::getTitle).toList());
  }

  public CustomQueryResult customQueryByMinPrice(BigDecimal minPrice) {
    List<Category> categories = categoryRepository.findCategoriesWithProductsAbovePrice(minPrice);
    return new CustomQueryResult(
        categories.size(),
        categories.stream().map(Category::getTitle).toList());
  }

  public LifecycleEventResult lifecycleDemo() {
    lifecycleEventsCollector.clear();
    Category category = new Category("Lifecycle Demo");
    category.addProduct(new CategoryProduct("Demo Product", "DEMO",
        BigDecimal.valueOf(42), new ProductDetails("Demo Inc", "SKU-DEMO"), null));
    Category saved = categoryRepository.save(category);
    categoryRepository.findById(saved.getId());
    return new LifecycleEventResult(lifecycleEventsCollector.snapshot());
  }

  public GlossaryResult glossaryDemo() {
    return new GlossaryResult(List.of(
        new GlossaryItem("Aggregate",
            "A cluster of domain objects treated as a single unit for data changes."),
        new GlossaryItem("Aggregate Root",
            "The entry point entity of an aggregate. Only root has a Repository."),
        new GlossaryItem("Entity",
            "An object with identity (id field)."),
        new GlossaryItem("Value Object",
            "An object without identity, embedded into entity (e.g. ProductDetails)."),
        new GlossaryItem("AggregateReference",
            "A typed ID reference to another aggregate root, replacing JPA associations."),
        new GlossaryItem("@MappedCollection",
            "Annotation declaring a one-to-many child collection inside an aggregate."),
        new GlossaryItem("@Embedded",
            "Annotation for embedding a value object's columns into the parent table."),
        new GlossaryItem("@Version",
            "Optimistic locking field incremented on each save."),
        new GlossaryItem("Lifecycle Events",
            "BeforeConvert, BeforeSave, AfterSave, AfterConvert — hooks during persistence operations."),
        new GlossaryItem("Repository",
            "Interface providing CRUD operations for an aggregate root.")
    ));
  }

  public EndpointListResult endpointsDemo() {
    return new EndpointListResult(List.of(
        new EndpointItem("GET", "/api/jdbc/differences", "JDBC vs JPA differences"),
        new EndpointItem("GET", "/api/jdbc/ddd", "DDD aggregates overview"),
        new EndpointItem("GET", "/api/jdbc/categories", "List all categories with products"),
        new EndpointItem("GET", "/api/jdbc/categories/{id}", "Get category by id"),
        new EndpointItem("POST", "/api/jdbc/categories", "Create category"),
        new EndpointItem("PUT", "/api/jdbc/categories/{id}", "Update category title"),
        new EndpointItem("DELETE", "/api/jdbc/categories/{id}", "Delete category"),
        new EndpointItem("POST", "/api/jdbc/categories/{id}/products", "Add product to category"),
        new EndpointItem("PUT", "/api/jdbc/categories/{catId}/products/{prodId}", "Update product"),
        new EndpointItem("DELETE", "/api/jdbc/categories/{catId}/products/{prodId}", "Remove product"),
        new EndpointItem("GET", "/api/jdbc/customers/{id}", "Get customer with orders"),
        new EndpointItem("POST", "/api/jdbc/customers", "Create customer"),
        new EndpointItem("POST", "/api/jdbc/customers/{id}/orders", "Add order to customer"),
        new EndpointItem("GET", "/api/jdbc/custom-query?title=...", "Search categories by title"),
        new EndpointItem("GET", "/api/jdbc/custom-query/min-price?minPrice=...", "Categories with products above price"),
        new EndpointItem("GET", "/api/jdbc/lifecycle", "Trigger and show lifecycle events"),
        new EndpointItem("GET", "/api/jdbc/glossary", "Glossary of terms"),
        new EndpointItem("GET", "/api/jdbc/endpoints", "List all endpoints")
    ));
  }

  private CategoryDto toCategoryDto(Category category) {
    return new CategoryDto(
        category.getId(),
        category.getTitle(),
        category.getProducts().stream().map(this::toProductDto).toList());
  }

  private ProductDto toProductDto(CategoryProduct p) {
    return new ProductDto(
        p.getId(), p.getName(), p.getType(), p.getPrice(),
        p.getDetails() != null ? p.getDetails().getManufacturer() : null,
        p.getDetails() != null ? p.getDetails().getSku() : null,
        p.getTag() != null ? p.getTag().getId() : null,
        p.getVersion());
  }

  private CustomerDto toCustomerDto(Customer customer) {
    return new CustomerDto(
        customer.getId(), customer.getName(), customer.getEmail(),
        customer.getOrders().stream()
            .map(o -> new CustomerOrderDto(o.getId(), o.getDescription()))
            .toList());
  }
}
