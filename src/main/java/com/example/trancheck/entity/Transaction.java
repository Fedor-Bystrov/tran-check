package com.example.trancheck.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Transaction {
  private final int id;
  private final BigDecimal amount;
  private final String data;

  public Transaction(int id, String amount, String data) {
    this.id = id;
    this.amount = amount == null || "".equals(amount)? null : new BigDecimal(amount);
    this.data = data;
  }

  public int getId() {
    return id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getData() {
    return data;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return id == that.id &&
      Objects.equals(amount, that.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, amount);
  }

  @Override
  public String toString() {
    return "Transaction{" +
      "id=" + id +
      ", amount=" + amount +
      ", data='" + data + '\'' +
      '}';
  }
}
