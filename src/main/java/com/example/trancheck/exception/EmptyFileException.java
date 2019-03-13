package com.example.trancheck.exception;

public class EmptyFileException extends RuntimeException {
  public EmptyFileException() {
    super("Provided file is empty");
  }
}
