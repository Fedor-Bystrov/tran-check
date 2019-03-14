package com.example.trancheck.report;


import com.example.trancheck.exception.UnsupportedFormatException;
import com.example.trancheck.parse.pojo.ParseResult;
import com.example.trancheck.report.pojo.TransactionsValidationReport;
import com.example.trancheck.report.producer.SimpleCSVReportProducer;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * ReportGenerator - утилитный класс, вызывает нужный ReportProducer
 * в зависимости от указанного ReportFormat
 */
@Component
public class ReportGenerator {

  private final SimpleCSVReportProducer simpleCSVReportProducer;

  public ReportGenerator(SimpleCSVReportProducer simpleCSVReportProducer) {
    this.simpleCSVReportProducer = simpleCSVReportProducer;
  }

  /**
   * Метод для записи отчета о сверке транзакций в файл
   *
   * @param parseResult  Результат обработки всего csv файла
   * @param report       Отчет об обработке успешно считанных из csv файла транзакций
   * @param path         Путь до папки, в которую положить отчет
   * @param reportFormat Формат отчета
   */
  public void writeToFile(ParseResult parseResult, TransactionsValidationReport report,
                          Path path, ReportFormat reportFormat) {
    switch (reportFormat) {
      case SIMPLE_CSV_REPORT:
        simpleCSVReportProducer.produce(parseResult, report, path);
        break;
      default:
        throw new UnsupportedFormatException();
    }
  }
}
