package com.example.springdatajpa.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductDetails {

  private String manufacturer;
  private String sku;

  public ProductDetails() {
  }

  public ProductDetails(String manufacturer, String sku) {
    this.manufacturer = manufacturer;
    this.sku = sku;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String getSku() {
    return sku;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }
}