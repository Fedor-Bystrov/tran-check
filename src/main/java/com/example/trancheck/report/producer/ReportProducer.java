package com.example.trancheck.report.producer;

import com.example.trancheck.parse.pojo.ParseResult;
import com.example.trancheck.report.pojo.TransactionsValidationReport;

import java.nio.file.Path;

/**
 * Общий интерфейс для все продьюсеров отчетов
 */
public interface ReportProducer {
  void produce(ParseResult parseResult, TransactionsValidationReport report, Path path);
}
