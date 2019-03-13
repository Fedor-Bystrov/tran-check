package com.example.trancheck.parse;

import java.util.Objects;

/**
 * ParseLineResult - результат успешно распознанной
 * строки в csv файле с транзакциями
 */
public class ParseLineResult {
  private String id;
  private String amount;
  private String data;
  private long lineNumber;

  public ParseLineResult(long lineNumber, String id, String amount, String data) {
    this.lineNumber = lineNumber;
    this.id = id;
    this.amount = amount;
    this.data = data;
  }

  public String getId() {
    return id;
  }

  public String getAmount() {
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
