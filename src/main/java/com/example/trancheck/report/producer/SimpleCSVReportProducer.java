package com.example.trancheck.report.producer;

import com.example.trancheck.parse.pojo.ParseResult;
import com.example.trancheck.report.pojo.TransactionsValidationReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class SimpleCSVReportProducer implements ReportProducer {
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCSVReportProducer.class);

  @Override
  public void produce(ParseResult parseResult, TransactionsValidationReport report, Path path) {
    LOGGER.info("Creating report file at {}", path);
  }
}
