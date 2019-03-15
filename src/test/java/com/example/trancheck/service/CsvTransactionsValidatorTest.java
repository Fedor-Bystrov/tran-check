package com.example.trancheck.service;

import com.example.trancheck.entity.Transaction;
import com.example.trancheck.parse.pojo.ParseLineResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvTransactionsValidatorTest {
  private static final List<ParseLineResult> LINES = List.of(
    new ParseLineResult(2, 123, "94.7", "20160101120000"),
    new ParseLineResult(3, 124, "150.75", "20160101120001"),
    new ParseLineResult(4, 125, "1020.2", "20160101120002"),
    new ParseLineResult(5, 126, "15.5", "20160101120003"),
    new ParseLineResult(6, 127, "120.74", "20160101120004")
  );
  private static final Set<Transaction> TXs = Set.of(
    new Transaction(123, "100.05", "20160101120000"),
    new Transaction(124, "150.75", "20160101120001"),
    new Transaction(125, "1010.00", "20160101120002"),
    new Transaction(126, "15.50", "20160101120003")
  );

  @Test
  void checkValidation() {
    final var csvTransactionsValidator = new CsvTransactionsValidator();
    final var validationReport = csvTransactionsValidator.check(LINES, TXs);
    final var reportEntries = validationReport.getReportEntries();
    assertEquals(reportEntries.get(LINES.get(0)), "Wrong transaction amount");
    assertEquals(reportEntries.get(LINES.get(1)), "Transaction approved");
    assertEquals(reportEntries.get(LINES.get(2)), "Wrong transaction amount");
    assertEquals(reportEntries.get(LINES.get(3)), "Transaction approved");
    assertEquals(reportEntries.get(LINES.get(4)), "Transaction was not found in DB");
  }
}
