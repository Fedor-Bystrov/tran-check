package com.example.trancheck.exception;

public class UndefinedCsvFilePropException extends RuntimeException {
  public UndefinedCsvFilePropException() {
    super("Property csvFile in config.properties is undefined");
  }
}
