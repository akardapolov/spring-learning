package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for REST Controllers.
 *
 * Demonstrates:
 * - @SpringBootTest with webEnvironment
 * - TestRestTemplate for API testing
 * - REST endpoint testing
 * - Response validation
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Section8_RestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/demo";
    }

    @Test
    void getAppInfoReturnsValidData() {
        ResponseEntity<Map> response =
                restTemplate.getForEntity(baseUrl() + "/app-info", Map.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("success")).isEqualTo(true);
        assertThat(response.getBody().get("data")).isNotNull();

        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        assertThat(data.get("name")).isNotNull();
        assertThat(data.get("version")).isNotNull();
    }

    @Test
    void getWelcomeMessageReturnsValidData() {
        ResponseEntity<Map> response =
                restTemplate.getForEntity(baseUrl() + "/welcome", Map.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("success")).isEqualTo(true);
        assertThat(response.getBody().get("data")).isNotNull();
        assertThat(response.getBody().get("data").toString()).contains("Welcome");
    }

    @Test
    void getGreetingWithDefaultName() {
        ResponseEntity<Map> response =
                restTemplate.getForEntity(baseUrl() + "/greeting", Map.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("data").toString()).contains("Guest");
    }

    @Test
    void getGreetingWithCustomName() {
        ResponseEntity<Map> response =
                restTemplate.getForEntity(baseUrl() + "/greeting?name=World", Map.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("data").toString()).contains("World");
    }

    @Test
    void getProfilesReturnsValidData() {
        ResponseEntity<Map> response =
                restTemplate.getForEntity(baseUrl() + "/profiles", Map.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("success")).isEqualTo(true);
        assertThat(response.getBody().get("data")).isNotNull();
    }

    @Test
    void getBeanCountReturnsNumber() {
        ResponseEntity<Map> response =
                restTemplate.getForEntity(baseUrl() + "/beans/count", Map.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("success")).isEqualTo(true);
        Object data = response.getBody().get("data");
        if (data instanceof Number) {
            assertThat(((Number) data).intValue()).isGreaterThan(0);
        } else {
            assertThat(data).isNotNull();
        }
    }

    @Test
    void customHealthCheckWorks() {
        ResponseEntity<Map> response =
                restTemplate.getForEntity(baseUrl() + "/health/custom", Map.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("success")).isEqualTo(true);
        assertThat(response.getBody().get("data")).isNotNull();
    }
}
