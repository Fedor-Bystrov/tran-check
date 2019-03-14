package com.example.trancheck.report.producer;

import com.example.trancheck.parse.pojo.ParseResult;
import com.example.trancheck.report.pojo.TransactionsValidationReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Component
public class SimpleReportProducer {
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReportProducer.class);

  public void produce(ParseResult parseResult, TransactionsValidationReport report, Path path) {
    LOGGER.info("Creating report file at {}", path);
    try (var writer = Files.newBufferedWriter(path, Charset.defaultCharset(), StandardOpenOption.CREATE_NEW)) {

      writer.write("PID;PAMOUNT;PDATA;RESULT;\n");
      for (var reportEntry : report.getReportEntries().entrySet()) {
        final var parseLineResult = reportEntry.getKey();
        final var parseLineMessage = reportEntry.getValue();
        writer.write(String.format("%d;%s;%s;%s\n",
          parseLineResult.getId(),
          parseLineResult.getAmount(),
          parseLineResult.getData(),
          parseLineMessage));
      }
    } catch (FileAlreadyExistsException x) {
      LOGGER.error("File {} already exists", path);
    } catch (IOException e) {
      LOGGER.error("Not able to create report", e);
    }
  }
}
