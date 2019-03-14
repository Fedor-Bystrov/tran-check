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
  private List<ParseLineResult> parseLineResults;

  /**
   * Строки которые не удалось распознать
   */
  private List<String> unparsedLines;

  public ParseResult() {
    parseLineResults = new ArrayList<>();
    unparsedLines = new ArrayList<>();
  }

  public void addParseLineResult(ParseLineResult parseResult) {
    parseLineResults.add(parseResult);
  }

  public List<ParseLineResult> getParseLineResults() {
    return parseLineResults;
  }

  public void addUnparsedLine(String line) {
    unparsedLines.add(line);
  }

  public List<String> getUnparsedLines() {
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
