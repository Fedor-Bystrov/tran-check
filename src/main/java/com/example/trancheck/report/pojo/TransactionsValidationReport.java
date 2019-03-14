package com.example.trancheck.report.pojo;

import com.example.trancheck.parse.pojo.ParseLineResult;

import java.util.HashMap;

/**
 * TransactionsValidationReport хранит в себе информацию
 * о валидации успешно прочитаных транзакций из файла.
 */
public class TransactionsValidationReport {
  private final HashMap<ParseLineResult, String> reportEntries;

  public TransactionsValidationReport() {
    this.reportEntries = new HashMap<>();
  }

  public void addEntry(ParseLineResult parseLineResult, String message){
    reportEntries.put(parseLineResult, message);
  }

  public HashMap<ParseLineResult, String> getReportEntries() {
    return reportEntries;
  }
}
