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

@Component
public class SimpleReportProducer implements ReportProducer {
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReportProducer.class);

  public void produce(ParseResult parseResult, TransactionsValidationReport report, Path path) {
    LOGGER.info("Creating report file at {}", path);
    try (var writer = Files.newBufferedWriter(path, Charset.defaultCharset(), StandardOpenOption.CREATE_NEW)) {

      writer.write("PID;PAMOUNT;PDATA;RESULT;\n");
      writer.write("RECOGNIZED;\n");
      for (var reportEntry : report.getReportEntries().entrySet()) {
        writeLine(writer, reportEntry.getKey(), reportEntry.getValue());
      }
      writer.write("MALFORMED;\n");
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
