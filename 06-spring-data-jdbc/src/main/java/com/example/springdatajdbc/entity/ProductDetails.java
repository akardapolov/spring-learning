package com.example.springdatajdbc.entity;

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

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }
}
