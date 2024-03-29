package com.example.trancheck.service;

import com.example.trancheck.entity.Transaction;
import com.example.trancheck.parse.pojo.ParseLineResult;
import com.example.trancheck.report.pojo.TransactionsValidationReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class CsvTransactionsValidator {
  private static final Logger LOGGER = LoggerFactory.getLogger(CsvTransactionsValidator.class);

  /**
   * Метод для получения отчена о сверке транзакций из файла с транзакциями в базе
   *
   * @param parseLineResults список успешно распарсеных транзакций из csv файла
   * @param transactions     список соответствующих им транзакций в базе,
   *                         либо пустой список если таких транзакций нет
   * @return Отчет о валидации
   */
  public TransactionsValidationReport check(Collection<ParseLineResult> parseLineResults, Set<Transaction> transactions) {
    LOGGER.info("Validating transactions");

    final var validationReport = new TransactionsValidationReport();
    for (var csvTransaction : parseLineResults) {
      // 1. Проверяем, есть ли транзакция из файла в базе
      final Optional<Transaction> txOptional = transactions.stream()
        .filter(tx -> tx.getId() == csvTransaction.getId())
        .findAny();

      if (txOptional.isEmpty()) {
        validationReport.addEntry(csvTransaction, "Transaction was not found in DB");
        continue;
      }

      // 2. Сверяем amount
      // В базе amount транзакции имеет два знака после зпт, приводим amount транзакции из csv к такому же виду
      final BigDecimal csvTxAmount = csvTransaction.getAmount().setScale(2, RoundingMode.HALF_DOWN);
      if (Objects.equals(csvTxAmount, txOptional.get().getAmount())) {
        validationReport.addEntry(csvTransaction, "Transaction approved");
      } else {
        validationReport.addEntry(csvTransaction, "Wrong transaction amount");
      }
    }

    return validationReport;
  }
}
