package com.example.trancheck.parse.pojo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * ParseLineResult - результат успешно распознанной
 * строки в csv файле с транзакциями
 */
public class ParseLineResult {
  private int id;
  private BigDecimal amount;
  private String data;
  private long lineNumber;

  public ParseLineResult(long lineNumber, int id, String amount, String data) {
    this.lineNumber = lineNumber;
    this.id = id;
    this.amount = new BigDecimal(amount);
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

  public long getLineNumber() {
    return lineNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ParseLineResult that = (ParseLineResult) o;
    return Objects.equals(id, that.id) &&
      Objects.equals(amount, that.amount) &&
      Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, amount, data);
  }

  @Override
  public String toString() {
    return "ParseLineResult{" +
      "id='" + id + '\'' +
      ", amount='" + amount + '\'' +
      ", data='" + data + '\'' +
      '}';
  }
}
