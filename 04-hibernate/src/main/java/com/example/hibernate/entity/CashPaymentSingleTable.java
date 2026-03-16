package com.example.hibernate.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CASH")
public class CashPaymentSingleTable extends PaymentSingleTable {

  private String cashDesk;

  public CashPaymentSingleTable() {
  }

  public CashPaymentSingleTable(String cashDesk) {
    super("cash payment");
    this.cashDesk = cashDesk;
  }

  public String getCashDesk() {
    return cashDesk;
  }
}