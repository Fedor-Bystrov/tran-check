package com.example.trancheck.report.producer;

import com.example.trancheck.parse.pojo.ParseLineResult;
import com.example.trancheck.parse.pojo.ParseResult;
import com.example.trancheck.report.pojo.TransactionsValidationReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Продьюсер отчета в формате:
 * <p>
 * <br/>
 * PID;PAMOUNT;PDATA;
 * <br/>
 * RECOGNIZED
 * <br/>
 * 123;94.7;20160101120000;
 * <br/>
 * MALFORMED
 * <br/>
 * 123;;20160101120000;
 * <br/>
 * <p>
 * где RECOGNIZED - список распознанных и сверенных строк,
 * а MALFORMED - список строк которые не смогли быть распарсены
 */
@Component
public class SimpleReportProducer implements ReportProducer {
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReportProducer.class);

  /**
   * Метод для записи отчета в csv файл
   *
   * @param parseResult Результат обработки csv файла с транзакциями
   * @param report      Отчет об обработке успешно считанных из csv файла транзакций
   * @param path        Путь до файла куда будет записан отчет
   */
  public void produce(ParseResult parseResult, TransactionsValidationReport report, Path path) {
    LOGGER.info("Creating report file at {}", path);
    try (var writer = Files.newBufferedWriter(path, Charset.defaultCharset(), StandardOpenOption.CREATE_NEW)) {

      // 1. Записываем все успешно распознанные строки
      writer.write("PID;PAMOUNT;PDATA;RESULT;\n");
      writer.write("RECOGNIZED;\n");
      for (var reportEntry : report.getReportEntries().entrySet()) {
        writeLine(writer, reportEntry.getKey(), reportEntry.getValue());
      }

      // 2. Записываем те что распознать не удалось
      writer.write("MALFORMED;\n");
      for (var line : parseResult.getUnparsedLines()) {
        writer.write(line);
      }
    } catch (FileAlreadyExistsException x) {
      LOGGER.error("File {} already exists", path);
    } catch (IOException e) {
      LOGGER.error("Not able to create report", e);
    }
  }

  private void writeLine(BufferedWriter writer, ParseLineResult parseLineResult, String message) throws IOException {
    writer.write(String.format("%d;%s;%s;%s;\n", parseLineResult.getId(), parseLineResult.getAmount(),
      parseLineResult.getData(), message));
  }
}
