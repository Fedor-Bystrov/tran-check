package com.example.trancheck.report.producer;

import com.example.trancheck.parse.pojo.ParseResult;
import com.example.trancheck.report.pojo.TransactionsValidationReport;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class SimpleCSVReportProducer implements ReportProducer {

  @Override
  public void produce(ParseResult parseResult, TransactionsValidationReport report, Path path) {

  }
}
