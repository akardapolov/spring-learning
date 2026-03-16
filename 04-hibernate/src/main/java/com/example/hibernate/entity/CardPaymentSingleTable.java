package com.example.hibernate.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CARD")
public class CardPaymentSingleTable extends PaymentSingleTable {

  private String cardNumber;

  public CardPaymentSingleTable() {
  }

  public CardPaymentSingleTable(String cardNumber) {
    super("card payment");
    this.cardNumber = cardNumber;
  }

  public String getCardNumber() {
    return cardNumber;
  }
}