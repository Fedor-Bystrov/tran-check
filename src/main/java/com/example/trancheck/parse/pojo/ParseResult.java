package com.example.trancheck.parse.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Результат обработки файла
 */
public class ParseResult {

  /**
   * Количество транзакций в CSV файле, указанное в последней строке
   */
  private long rowsAmount;

  /**
   * Присутствовали ли верные хэдеры в файле
   */
  private boolean headersOk;

  /**
   * Список успешно распознанных строк
   */
  private List<ParseLineResult> parseResults;

  /**
   * Номера строк которые не удалось распознать
   */
  private List<Long> unparsedLines;

  public ParseResult() {
    parseResults = new ArrayList<>();
    unparsedLines = new ArrayList<>();
  }

  public void addParseLineResult(ParseLineResult parseResult) {
    parseResults.add(parseResult);
  }

  public List<ParseLineResult> getParseResults() {
    return parseResults;
  }

  public void addUnparsedLine(long lineNumber) {
    unparsedLines.add(lineNumber);
  }

  public List<Long> getUnparsedLines() {
    return unparsedLines;
  }

  public long getRowsAmount() {
    return rowsAmount;
  }

  public void setRowsAmount(long rowsAmount) {
    this.rowsAmount = rowsAmount;
  }

  public boolean isHeadersOk() {
    return headersOk;
  }

  public void setHeadersOk(boolean headersOk) {
    this.headersOk = headersOk;
  }
}
