package com.example.trancheck;

import com.example.trancheck.controller.CsvTransactionController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TranCheckApplication implements CommandLineRunner {
  private CsvTransactionController csvTransactionController;

  public TranCheckApplication(CsvTransactionController csvTransactionController) {
    this.csvTransactionController = csvTransactionController;
  }

  public static void main(String[] args) {
    SpringApplication.run(TranCheckApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    csvTransactionController.checkTransactions();
  }
}
