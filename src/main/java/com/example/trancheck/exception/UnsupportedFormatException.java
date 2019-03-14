package com.example.trancheck.exception;

public class UnsupportedFormatException extends RuntimeException {
  public UnsupportedFormatException() {
    super("Unknown generator format");
  }
}
