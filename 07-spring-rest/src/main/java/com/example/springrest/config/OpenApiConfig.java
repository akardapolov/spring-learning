package com.example.springrest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    Server server = new Server();
    server.setUrl("http://localhost:8891");
    server.setDescription("Development Server");

    Contact contact = new Contact();
    contact.setEmail("example@spring-rest.com");
    contact.setName("Spring REST Demo");

    License license = new License()
        .name("Apache 2.0")
        .url("https://www.apache.org/licenses/LICENSE-2.0");

    Info info = new Info()
        .title("Spring REST API")
        .version("1.0")
        .contact(contact)
        .description("REST API documentation for Spring Learning Project. " +
            "Demonstrates various REST concepts including controllers, request handling, " +
            "response handling, exception handling, validation, CRUD operations, " +
            "pagination, sorting, and HTTP contracts.")
        .license(license);

    return new OpenAPI()
        .info(info)
        .servers(List.of(server));
  }
}
