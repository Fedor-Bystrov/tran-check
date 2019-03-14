package com.example.trancheck.controller;

import com.example.trancheck.exception.EmptyFileException;
import com.example.trancheck.exception.UndefinedCsvFilePropException;
import com.example.trancheck.service.CsvTransactionReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Properties;

@Controller
public class CsvTransactionController {
  private static final Logger LOGGER = LoggerFactory.getLogger(CsvTransactionController.class);

  private final CsvTransactionReportService reportService;

  public CsvTransactionController(CsvTransactionReportService reportService) {
    this.reportService = reportService;
  }

  /**
  /**
   * Контроллер, осуществляющий сверку транзакций из файла с транзакциями в базе
   */
  public void checkTransactions() {
    LOGGER.info("Transaction check initialized");
    try {
      final var csvFileProp = readProperties().getProperty("csvFile");
      if (csvFileProp == null || "".equals(csvFileProp)) {
        throw new UndefinedCsvFilePropException();
      }

      reportService.execute(Path.of(csvFileProp));
    } catch (NoSuchFileException e) {
      LOGGER.error(String.format("File %s not found", e.getMessage()));
    } catch (EmptyFileException | UndefinedCsvFilePropException e) {
      LOGGER.error(e.getMessage());
    } catch (IOException e) {
      LOGGER.error("Error reading file", e);
    }
  }

  // читаем config.properties
  private Properties readProperties() {
    Properties prop = new Properties();
    try (var input = new FileInputStream("config.properties")) {
      prop.load(input);
    } catch (FileNotFoundException e) {
      LOGGER.error("Cannot find config.properties. It should be next to jar file");
    } catch (IOException e) {
      LOGGER.error("Cannot read config.properties.", e);
    }
    return prop;
  }
}
