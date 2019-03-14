package com.example.trancheck.service;

import com.example.trancheck.parse.TransactionsFileParser;
import com.example.trancheck.parse.pojo.ParseLineResult;
import com.example.trancheck.report.ReportGenerator;
import com.example.trancheck.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.trancheck.report.ReportFormat.SIMPLE_CSV_REPORT;

/**
 * Сервис сверки транзакций полученных из вне с транзакциями в базе
 */
@Service
public class TransactionReportService {
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionReportService.class);

  private final TransactionsFileParser fileParser;
  private final TransactionRepository transactionRepository;
  private final CsvTransactionsValidator csvTransactionsValidator;
  private final ReportGenerator reportGenerator;

  public TransactionReportService(TransactionsFileParser fileParser, TransactionRepository transactionRepository,
                                  CsvTransactionsValidator csvTransactionsValidator,
                                  ReportGenerator reportGenerator) {
    this.fileParser = fileParser;
    this.transactionRepository = transactionRepository;
    this.csvTransactionsValidator = csvTransactionsValidator;
    this.reportGenerator = reportGenerator;
  }

  // TODO:
  //  1. Генерить репорт (учесть "форматов отчёта может быть несколько (реализовать нужно только один)")
  //  2. Собрать jarник, подтюнить pom
  //  3. Юнит тесты
  //  4. Readme

  /**
   * Метод для сверки транзакций из CSV файла с транзакциями в базе.
   * По окончанию, создает файл с результатом сверки
   *
   * @param pathToFile путь до csv файла с транзакциями
   * @throws IOException
   */
  public void processCsv(Path pathToFile) throws IOException {
    LOGGER.info("Processing csv file");

    // 1. Парсим csv файл с транзакциями
    final var parseResult = fileParser.parse(pathToFile);

    // 2. Собираем список id транзакций
    final Set<Integer> csvTransactionsIds = parseResult.getParseLineResults()
      .stream()
      .mapToInt(ParseLineResult::getId)
      .boxed()
      .collect(Collectors.toSet());

    // 3. Запрашиваем транзакции с такими id из базы
    final var transactions = new HashSet<>(transactionRepository.getTransactions(csvTransactionsIds));

    // 4. Сверяем успешно распознанные транзакции из файла с транзакциями в базе
    final var validationReport = csvTransactionsValidator.check(parseResult.getParseLineResults(), transactions);

    // 5. Создаем отчет
    reportGenerator.writeToFile(parseResult, validationReport, pathToFile, SIMPLE_CSV_REPORT);
  }
}
