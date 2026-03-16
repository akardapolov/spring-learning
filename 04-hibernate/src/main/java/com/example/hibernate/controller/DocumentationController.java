package com.example.hibernate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentationController {

  @GetMapping("/")
  public String index() {
    return "forward:/index.html";
  }
}