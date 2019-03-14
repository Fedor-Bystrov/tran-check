package com.example.trancheck.report.pojo;

import com.example.trancheck.parse.pojo.ParseLineResult;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * TransactionsValidationReport хранит в себе информацию
 * о валидации успешно прочитаных транзакций из файла.
 * Записи упорядочены по номеру строки в файле с транзакциями.
 */
public class TransactionsValidationReport {
  private final TreeMap<ParseLineResult, String> reportEntries;

  public TransactionsValidationReport() {
    this.reportEntries = new TreeMap<>(Comparator.comparingLong(ParseLineResult::getLineNumber));
  }

  public void addEntry(ParseLineResult parseLineResult, String message) {
    reportEntries.put(parseLineResult, message);
  }

  public TreeMap<ParseLineResult, String> getReportEntries() {
    return reportEntries;
  }
}
