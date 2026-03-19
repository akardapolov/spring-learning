package com.example.springdatajdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class Section8_MockMvcIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldGetDifferences() throws Exception {
    mockMvc.perform(get("/api/jdbc/differences"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.items").isArray())
        .andExpect(jsonPath("$.data.items.length()", greaterThanOrEqualTo(5)));
  }

  @Test
  void shouldGetDddInfo() throws Exception {
    mockMvc.perform(get("/api/jdbc/ddd"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.aggregates.length()").value(3));
  }

  @Test
  void shouldGetAllCategories() throws Exception {
    mockMvc.perform(get("/api/jdbc/categories"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(3)));
  }

  @Test
  void shouldGetCategoryById() throws Exception {
    mockMvc.perform(get("/api/jdbc/categories/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.title").isNotEmpty())
        .andExpect(jsonPath("$.data.products").isArray());
  }

  @Test
  void shouldReturn404ForUnknownCategory() throws Exception {
    mockMvc.perform(get("/api/jdbc/categories/99999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorCode").value("NOT_FOUND"));
  }

  @Test
  void shouldCreateCategoryViaPost() throws Exception {
    mockMvc.perform(post("/api/jdbc/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\": \"Music\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.title").value("Music"))
        .andExpect(jsonPath("$.data.id").isNumber());
  }

  @Test
  void shouldAddProductToCategory() throws Exception {
    String catResponse = mockMvc.perform(post("/api/jdbc/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\": \"Garden\"}"))
        .andReturn().getResponse().getContentAsString();

    String idStr = catResponse.replaceAll(".*\"id\":(\\d+).*", "$1");

    mockMvc.perform(post("/api/jdbc/categories/" + idStr + "/products")
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
        .andExpect(jsonPath("$.data.products.length()").value(1))
        .andExpect(jsonPath("$.data.products[0].name").value("Shovel"));
  }

  @Test
  void shouldCreateCustomerAndAddOrder() throws Exception {
    String custResponse = mockMvc.perform(post("/api/jdbc/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"Test User\", \"email\": \"test@test.com\"}"))
        .andReturn().getResponse().getContentAsString();

    String idStr = custResponse.replaceAll(".*\"id\":(\\d+).*", "$1");

    mockMvc.perform(post("/api/jdbc/customers/" + idStr + "/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"description\": \"Test order\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.orders.length()").value(1));
  }

  @Test
  void shouldSearchByTitle() throws Exception {
    mockMvc.perform(get("/api/jdbc/custom-query").param("title", "oo"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.count", greaterThan(0)))
        .andExpect(jsonPath("$.data.categoryTitles", hasItem("Books")));
  }

  @Test
  void shouldSearchByMinPrice() throws Exception {
    mockMvc.perform(get("/api/jdbc/custom-query/min-price").param("minPrice", "1000"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.count", greaterThan(0)));
  }

  @Test
  void shouldShowLifecycleEvents() throws Exception {
    mockMvc.perform(get("/api/jdbc/lifecycle"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.events").isArray())
        .andExpect(jsonPath("$.data.events.length()", greaterThan(0)));
  }

  @Test
  void shouldShowGlossary() throws Exception {
    mockMvc.perform(get("/api/jdbc/glossary"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.items.length()", greaterThanOrEqualTo(6)));
  }

  @Test
  void shouldShowEndpoints() throws Exception {
    mockMvc.perform(get("/api/jdbc/endpoints"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.endpoints").isArray());
  }
}
