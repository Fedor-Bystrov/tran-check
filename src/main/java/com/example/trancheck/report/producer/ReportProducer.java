package com.example.trancheck.report.producer;

import com.example.trancheck.parse.pojo.ParseResult;
import com.example.trancheck.report.ReportFormat;
import com.example.trancheck.report.pojo.TransactionsValidationReport;

public interface ReportProducer {
  void produce(ParseResult parseResult, TransactionsValidationReport report, ReportFormat reportFormat);
}
