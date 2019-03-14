package com.example.trancheck.report.pojo;

import com.example.trancheck.parse.pojo.ParseLineResult;

import java.util.HashMap;

/**
 * TransactionValidationReport хранит в себе информацию
 * о валидации успешно прочитаных транзакций из файла.
 */
public class TransactionValidationReport {
  private final HashMap<ParseLineResult, String> reportEntries;

  public TransactionValidationReport() {
    this.reportEntries = new HashMap<>();
  }

  public void addEntry(ParseLineResult parseLineResult, String message){
    reportEntries.put(parseLineResult, message);
  }

  public HashMap<ParseLineResult, String> getReportEntries() {
    return reportEntries;
  }
}
