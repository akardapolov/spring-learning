package com.example.springrest;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springrest.model.ApiModels.ControllerBasicsResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Section1_ControllerBasicsTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void whenGetBasics_thenReturnControllerBasicsInfo() {
    ResponseEntity<ControllerBasicsResult> response = restTemplate.getForEntity(
        "/api/rest/basics",
        ControllerBasicsResult.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    ControllerBasicsResult result = response.getBody();
    assertThat(result).isNotNull();
    assertThat(result.controllerType()).isEqualTo("RestController");
    assertThat(result.availableAnnotations()).contains("@RestController", "@GetMapping", "@PostMapping");
    assertThat(result.httpMethodMappings()).containsKey("GET");
    assertThat(result.httpMethodMappings()).containsKey("POST");
  }
}
