package com.example.trancheck.service;

import com.example.trancheck.entity.Transaction;
import com.example.trancheck.parse.pojo.ParseLineResult;
import com.example.trancheck.report.pojo.TransactionsValidationReport;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class CsvTransactionValidator {

  /**
   * Метод для получения отчена о сверке транзакций из файла с транзакциями в базе
   *
   * @param parseLineResults список успешно распарсеных транзакций из csv файла
   * @param transactions     список соответствующих им транзакций в базе,
   *                         либо пустой список если таких транзакций нет
   * @return Отчет о валидации
   */
  public TransactionsValidationReport check(Collection<ParseLineResult> parseLineResults, Set<Transaction> transactions) {
    final var validationReport = new TransactionsValidationReport();

    for (var csvTransaction : parseLineResults) {
      // 1. Проверяем, есть ли транзакция из файла в базе
      final Optional<Transaction> txOptional = transactions.stream()
        .filter(tx -> tx.getId() == csvTransaction.getId())
        .findAny();

      if (txOptional.isEmpty()) {
        validationReport.addEntry(csvTransaction, "Транзакция с таким ID не найдена в базе.");
        continue;
      }

      // 2. Сверяем amount
      // В базе amount транзакции имеет два знака после зпт, приводим amount транзакции из csv к такому же виду
      final BigDecimal csvTxAmount = csvTransaction.getAmount().setScale(2, RoundingMode.HALF_DOWN);
      if (Objects.equals(csvTxAmount, txOptional.get().getAmount())) {
        validationReport.addEntry(csvTransaction, "Транзакция подтверждена.");
      } else {
        validationReport.addEntry(csvTransaction, "Значение в поле amount не совпадает с транзакцией в базе.");
      }
    }

    return validationReport;
  }
}
