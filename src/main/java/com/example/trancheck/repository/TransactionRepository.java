package com.example.trancheck.repository;

import com.example.trancheck.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TransactionRepository {
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionRepository.class);

  private final JdbcTemplate jdbcTemplate;

  public TransactionRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Transaction> getTransactions() {
    LOGGER.info("Fetching transactions from database");
    return jdbcTemplate.query("SELECT id, amount, data FROM transactions", this::mapRow);
  }

  private Transaction mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new Transaction(
      resultSet.getInt("id"),
      resultSet.getString("amount"),
      resultSet.getString("data")
    );
  }
}
