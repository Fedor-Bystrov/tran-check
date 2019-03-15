package com.example.trancheck.report;

import com.example.trancheck.parse.pojo.ParseResult;
import com.example.trancheck.report.pojo.TransactionsValidationReport;
import com.example.trancheck.report.producer.SimpleReportProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static com.example.trancheck.report.ReportFormat.SIMPLE_REPORT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReportGeneratorTest {

  @Mock
  SimpleReportProducer simpleReportProducer;
  @InjectMocks
  ReportGenerator reportGenerator;

  @Test
  @DisplayName("check SIMPLE_REPORT called")
  void writeToFileSimpleReportTest() {
    reportGenerator.writeToFile(
      new ParseResult(),
      new TransactionsValidationReport(),
      Path.of(""),
      SIMPLE_REPORT
    );
    verify(simpleReportProducer).produce(
      any(ParseResult.class),
      any(TransactionsValidationReport.class),
      any(Path.class));
  }
}
