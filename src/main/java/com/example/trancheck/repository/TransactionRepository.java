package com.example.trancheck.repository;

import com.example.trancheck.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Repository
public class TransactionRepository {
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionRepository.class);

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public TransactionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * Метод для получения списка транзакций по их id
   *
   * @param transactionIds множество id транзакций
   * @return список транзакций
   */
  public List<Transaction> getTransactions(Set<Integer> transactionIds) {
    LOGGER.info("Fetching transactions from database");
    return jdbcTemplate.query(
      "SELECT id, amount, data FROM transactions WHERE id IN (:ids)",
      new MapSqlParameterSource("ids", transactionIds),
      this::mapRow);
  }

  private Transaction mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new Transaction(
      resultSet.getInt("id"),
      resultSet.getString("amount"),
      resultSet.getString("data")
    );
  }
}
