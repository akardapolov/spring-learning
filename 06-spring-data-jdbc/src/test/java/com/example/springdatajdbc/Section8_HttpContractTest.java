package com.example.springdatajdbc;

import com.example.springdatajdbc.repository.CategoryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class Section8_HttpContractTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  void shouldReturnExactDifferencesPayload() throws Exception {
    mockMvc.perform(get("/api/jdbc/differences"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.items.length()").value(7))
        .andExpect(jsonPath("$.data.items[*].topic", contains(
            "Persistence Context",
            "Lazy Loading",
            "Dirty Checking",
            "Relations",
            "Saving",
            "Identity",
            "Schema"
        )));
  }

  @Test
  void shouldReturnSeededCategories() throws Exception {
    mockMvc.perform(get("/api/jdbc/categories"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.length()").value(3))
        .andExpect(jsonPath("$.data[*].title", containsInAnyOrder(
            "Electronics",
            "Books",
            "Home"
        )));
  }

  @Test
  void shouldReturnSeededCategoryByDynamicId() throws Exception {
    Long electronicsId = categoryRepository.findByTitle("Electronics").orElseThrow().getId();

    mockMvc.perform(get("/api/jdbc/categories/{id}", electronicsId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.id").value(electronicsId))
        .andExpect(jsonPath("$.data.title").value("Electronics"))
        .andExpect(jsonPath("$.data.products.length()").value(2))
        .andExpect(jsonPath("$.data.products[*].name", containsInAnyOrder("Phone", "Laptop")));
  }

  @Test
  void shouldReturnExactErrorContractForUnknownCategory() throws Exception {
    mockMvc.perform(get("/api/jdbc/categories/99999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.errorCode").value("NOT_FOUND"))
        .andExpect(jsonPath("$.message").value("Category not found: 99999"));
  }

  @Test
  void shouldCreateUpdateAndDeleteCategoryViaHttp() throws Exception {
    MvcResult createResult = mockMvc.perform(post("/api/jdbc/categories")
                                                 .contentType(MediaType.APPLICATION_JSON)
                                                 .content("""
                {
                  "title": "Music"
                }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.title").value("Music"))
        .andExpect(jsonPath("$.data.products.length()").value(0))
        .andReturn();

    long categoryId = extractDataId(createResult);

    mockMvc.perform(put("/api/jdbc/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "title": "Music & Audio"
                }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.id").value(categoryId))
        .andExpect(jsonPath("$.data.title").value("Music & Audio"));

    mockMvc.perform(delete("/api/jdbc/categories/{id}", categoryId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value("deleted"));

    mockMvc.perform(get("/api/jdbc/categories/{id}", categoryId))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorCode").value("NOT_FOUND"))
        .andExpect(jsonPath("$.message").value("Category not found: " + categoryId));
  }

  @Test
  void shouldAddProductToCategoryViaHttpAndPersistFields() throws Exception {
    MvcResult createCategoryResult = mockMvc.perform(post("/api/jdbc/categories")
                                                         .contentType(MediaType.APPLICATION_JSON)
                                                         .content("""
                {
                  "title": "Garden"
                }
                """))
        .andExpect(status().isOk())
        .andReturn();

    long categoryId = extractDataId(createCategoryResult);

    mockMvc.perform(post("/api/jdbc/categories/{id}/products", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "name": "Shovel",
                  "type": "TOOL",
                  "price": 35.00,
                  "manufacturer": "GardenCo",
                  "sku": "SKU-SHOVEL",
                  "tagId": null
                }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.id").value(categoryId))
        .andExpect(jsonPath("$.data.title").value("Garden"))
        .andExpect(jsonPath("$.data.products.length()").value(1))
        .andExpect(jsonPath("$.data.products[0].name").value("Shovel"))
        .andExpect(jsonPath("$.data.products[0].type").value("TOOL"))
        .andExpect(jsonPath("$.data.products[0].manufacturer").value("GardenCo"))
        .andExpect(jsonPath("$.data.products[0].sku").value("SKU-SHOVEL"))
        .andExpect(jsonPath("$.data.products[0].tagId", nullValue()));

    mockMvc.perform(get("/api/jdbc/categories/{id}", categoryId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.products.length()").value(1))
        .andExpect(jsonPath("$.data.products[0].name").value("Shovel"));
  }

  @Test
  void shouldCreateCustomerAndAddOrderViaHttp() throws Exception {
    MvcResult createCustomerResult = mockMvc.perform(post("/api/jdbc/customers")
                                                         .contentType(MediaType.APPLICATION_JSON)
                                                         .content("""
                {
                  "name": "Test User",
                  "email": "test@test.com"
                }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.name").value("Test User"))
        .andExpect(jsonPath("$.data.email").value("test@test.com"))
        .andExpect(jsonPath("$.data.orders.length()").value(0))
        .andReturn();

    long customerId = extractDataId(createCustomerResult);

    mockMvc.perform(post("/api/jdbc/customers/{id}/orders", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "description": "Test order"
                }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.id").value(customerId))
        .andExpect(jsonPath("$.data.orders.length()").value(1))
        .andExpect(jsonPath("$.data.orders[0].description").value("Test order"));

    mockMvc.perform(get("/api/jdbc/customers/{id}", customerId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.id").value(customerId))
        .andExpect(jsonPath("$.data.orders.length()").value(1))
        .andExpect(jsonPath("$.data.orders[0].description").value("Test order"));
  }

  @Test
  void shouldReturnExactCustomQueryResults() throws Exception {
    mockMvc.perform(get("/api/jdbc/custom-query").param("title", "oo"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.count").value(1))
        .andExpect(jsonPath("$.data.categoryTitles", contains("Books")));

    mockMvc.perform(get("/api/jdbc/custom-query/min-price").param("minPrice", "1000"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.count").value(1))
        .andExpect(jsonPath("$.data.categoryTitles", contains("Electronics")));
  }

  @Test
  void shouldReturnLifecycleEventsInExactOrder() throws Exception {
    mockMvc.perform(get("/api/jdbc/lifecycle"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.events", contains(
            "BeforeConvert: Lifecycle Demo",
            "BeforeSave: Lifecycle Demo",
            "AfterSave: Lifecycle Demo",
            "AfterConvert: Lifecycle Demo"
        )));
  }

  @Test
  void shouldReturnGlossaryAndEndpointsPayloads() throws Exception {
    mockMvc.perform(get("/api/jdbc/glossary"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.items.length()").value(10))
        .andExpect(jsonPath("$.data.items[*].term", contains(
            "Aggregate",
            "Aggregate Root",
            "Entity",
            "Value Object",
            "AggregateReference",
            "@MappedCollection",
            "@Embedded",
            "@Version",
            "Lifecycle Events",
            "Repository"
        )));

    mockMvc.perform(get("/api/jdbc/endpoints"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.endpoints.length()").value(18));
  }

  private long extractDataId(MvcResult result) throws Exception {
    JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());
    return root.path("data").path("id").asLong();
  }
}