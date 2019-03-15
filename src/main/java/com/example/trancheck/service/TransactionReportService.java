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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.trancheck.report.ReportFormat.SIMPLE_REPORT;

/**
 * Сервис сверки транзакций полученных из вне с транзакциями в базе
 */
@Service
public class TransactionReportService {
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionReportService.class);
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

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

  /**
   * Метод для сверки транзакций из CSV файла с транзакциями в базе.
   * По окончанию, создает файл с результатом сверки
   *
   * @param pathToFile путь до csv файла с транзакциями
   * @throws IOException
   */
  public void processCsv(Path pathToFile) throws IOException {
    LOGGER.info("Processing csv file {}", pathToFile.toAbsolutePath().normalize());

    // 1. Парсим csv файл с транзакциями
    final var parseResult = fileParser.parse(pathToFile);

    // 2. Собираем список id транзакций из файла
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
    reportGenerator.writeToFile(parseResult, validationReport, getReportFilePath(pathToFile), SIMPLE_REPORT);
  }

  private Path getReportFilePath(Path pathToCsv) {
    final var newFileName = String.format("report#%s.csv", FORMATTER.format(LocalDateTime.now()));
    return pathToCsv.getParent()
      .toAbsolutePath()
      .normalize()
      .resolve(newFileName);
  }
}
