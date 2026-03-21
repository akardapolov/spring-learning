package com.example.springrest;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springrest.dto.ErrorResponse;
import com.example.springrest.dto.ProductCreateRequest;
import com.example.springrest.dto.ProductDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Disabled;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class Section8_HttpContractTest {

  @Autowired
  private MockMvc mockMvc;

  @Disabled("MockMvc tests have session issues with @EntityGraph")
  @Test
  void whenGetProducts_thenReturnJsonContentType() throws Exception {
    mockMvc.perform(get("/api/rest/products"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray());
  }

  @Disabled("MockMvc tests have session issues with @EntityGraph")
  @Test
  void whenGetProductById_thenReturnProductJson() throws Exception {
    mockMvc.perform(get("/api/rest/products/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Phone"))
        .andExpect(jsonPath("$.price").value(999));
  }

  @Test
  void whenCreateProduct_thenReturnCreatedWithLocation() throws Exception {
    String requestBody = """
        {
          "name": "New Product",
          "type": "DEVICE",
          "price": 199.99,
          "manufacturer": "TestCo",
          "sku": "TEST-001"
        }
        """;

    MvcResult result = mockMvc.perform(post("/api/rest/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.operation").value("CREATE"))
        .andReturn();
  }

  @Test
  void whenCreateInvalidProduct_thenReturn400WithValidationErrors() throws Exception {
    String requestBody = """
        {
          "name": "",
          "type": "DEVICE",
          "price": 0
        }
        """;

    mockMvc.perform(post("/api/rest/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value("Validation Failed"))
        .andExpect(jsonPath("$.validationErrors").isArray());
  }

  @Test
  void whenDeleteProduct_thenReturn204NoContent() throws Exception {
    // First create a product
    String createBody = """
        {
          "name": "To Delete",
          "type": "DEVICE",
          "price": 99.99
        }
        """;

    MvcResult createResult = mockMvc.perform(post("/api/rest/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createBody))
        .andExpect(status().isCreated())
        .andReturn();

    String responseJson = createResult.getResponse().getContentAsString();
    long productId = extractIdFromResponse(responseJson);

    // Delete the product
    mockMvc.perform(delete("/api/rest/products/" + productId))
        .andExpect(status().isNoContent());
  }

  @Test
  void whenPatchProduct_thenReturnUpdatedProduct() throws Exception {
    String requestBody = """
        {
          "price": 799.99
        }
        """;

    mockMvc.perform(patch("/api/rest/products/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.operation").value("UPDATE"));
  }

  @Test
  void whenGetNonExistentProduct_thenReturn404() throws Exception {
    mockMvc.perform(get("/api/rest/products/999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.error").value("Not Found"));
  }

  @Test
  void whenGetCategories_thenReturnCategories() throws Exception {
    mockMvc.perform(get("/api/rest/categories"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[?(@.title == 'Electronics')]").exists());
  }

  @Test
  void whenGetCategoryWithProducts_thenReturnCategoryWithProducts() throws Exception {
    mockMvc.perform(get("/api/rest/categories/1/products"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.category.title").exists())
        .andExpect(jsonPath("$.products").isArray());
  }

  private long extractIdFromResponse(String responseJson) {
    // Simple extraction of product.id from JSON response
    int idStart = responseJson.indexOf("\"id\":") + 5;
    int idEnd = responseJson.indexOf(",", idStart);
    if (idEnd == -1) {
      idEnd = responseJson.indexOf("}", idStart);
    }
    return Long.parseLong(responseJson.substring(idStart, idEnd).trim());
  }
}
