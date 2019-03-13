package com.example.trancheck.service;

import com.example.trancheck.parse.TransactionsFileParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class CsvTransactionReportService {

  private final TransactionsFileParser fileParser;

  public CsvTransactionReportService(TransactionsFileParser fileParser) {
    this.fileParser = fileParser;
  }

  public void execute(Path pathToFile) throws IOException {
    final var parseResult = fileParser.parse(pathToFile);
    // TODO 1. Подтягиваем все транзакции из базы
    // TODO 2. Сматчить успешные строки в parseResult с транзакциями из базы (список правил)
    // TODO 3. Генерить репорт (учесть "форматов отчёта может быть несколько (реализовать нужно только один)")
    System.out.println(parseResult.getRowsAmount());
  }
}
