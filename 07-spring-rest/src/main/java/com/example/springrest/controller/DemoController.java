package com.example.springrest.controller;

import com.example.springrest.exception.ProductNotFoundException;
import com.example.springrest.model.ApiModels;
import com.example.springrest.service.RestDemoService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest")
public class DemoController {

  private final RestDemoService demoService;

  public DemoController(RestDemoService demoService) {
    this.demoService = demoService;
  }

  // Section 1: Controller Basics
  @GetMapping("/basics")
  public ApiModels.ControllerBasicsResult getControllerBasics() {
    return demoService.getControllerBasics();
  }

  // Section 2: HTTP Methods
  @GetMapping("/http-methods")
  public ResponseEntity<String> getHttpMethods() {
    return ResponseEntity.ok("GET method - read resource");
  }

  @PostMapping("/http-methods")
  public ResponseEntity<String> postHttpMethods() {
    return ResponseEntity.status(HttpStatus.CREATED).body("POST method - create resource");
  }

  @PutMapping("/http-methods")
  public ResponseEntity<String> putHttpMethods() {
    return ResponseEntity.ok("PUT method - update entire resource");
  }

  @GetMapping("/http-methods/head")
  public ResponseEntity<Void> headHttpMethods() {
    return ResponseEntity.ok().build();
  }

  // Section 3: Request Parameters
  @GetMapping("/parameters/path/{id}")
  public ApiModels.RequestParametersDemo getPathVariable(
      @PathVariable String id,
      @RequestParam(defaultValue = "default") String queryParam,
      @RequestHeader Map<String, String> headers) {

    return demoService.demonstrateParameters(id, queryParam, headers, null);
  }

  @PostMapping("/parameters/body")
  public ApiModels.RequestParametersDemo getRequestBody(
      @RequestBody Map<String, Object> body,
      @RequestHeader Map<String, String> headers) {

    return demoService.demonstrateParameters(null, null, headers, body);
  }

  // Section 4: Response Handling
  @GetMapping("/response/{type}")
  public ResponseEntity<ApiModels.ResponseHandlingDemo> demonstrateResponse(
      @PathVariable String type) {

    ApiModels.ResponseHandlingDemo demo = demoService.demonstrateResponse(type);
    return ResponseEntity.status(demo.statusCode()).body(demo);
  }

  @GetMapping("/response/custom-headers")
  public ResponseEntity<String> customHeaders() {
    return ResponseEntity.ok()
        .header("X-Custom-Header", "custom-value")
        .header("X-Application-Name", "Spring Rest Demo")
        .body("Response with custom headers");
  }

  // Section 5: Exception Handling
  @GetMapping("/exception/{type}")
  public ApiModels.ExceptionHandlingDemo demonstrateException(@PathVariable String type) {
    return demoService.demonstrateExceptionHandling(type);
  }

  @GetMapping("/exception/not-found")
  public ResponseEntity<String> triggerNotFound() {
    throw new ProductNotFoundException("This triggers a 404 response");
  }

  @GetMapping("/exception/bad-request")
  public ResponseEntity<String> triggerBadRequest() {
    throw new IllegalArgumentException("This triggers a 400 response");
  }

  // Section 6: Validation Demo
  @PostMapping("/validation")
  public ApiModels.ValidationDemo validateProduct(
      @RequestBody com.example.springrest.dto.ProductCreateRequest request) {

    return demoService.demonstrateValidation(request);
  }
}
