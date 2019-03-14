package com.example.trancheck.report.producer;

import com.example.trancheck.parse.pojo.ParseResult;
import com.example.trancheck.report.pojo.TransactionsValidationReport;

import java.nio.file.Path;

public interface ReportProducer {
  void produce(ParseResult parseResult, TransactionsValidationReport report, Path path);
}
