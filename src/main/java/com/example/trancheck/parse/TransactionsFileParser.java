package com.example.trancheck.parse;

import com.example.trancheck.exception.EmptyFileException;
import com.example.trancheck.parse.pojo.ParseLineResult;
import com.example.trancheck.parse.pojo.ParseResult;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

@Component
public class TransactionsFileParser {
  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TransactionsFileParser.class);

  private static final Pattern HEADERS_PATTERN = Pattern.compile("^PID;PAMOUNT;PDATA;?$");
  // Считаем что отрицательных amount нет
  private static final Pattern DATA_PATTERN = Pattern.compile("^(\\d+);(\\d+\\.\\d+);(.*);?$");
  private static final Pattern TOTAL_PATTERN = Pattern.compile("^TOTAL;(\\d+);?$");

  /**
   * Метод для парсинга csv файла с транзакциями в формате:
   * <br/>
   * PID;PAMOUNT;PDATA;
   * <br/>
   * 123;94.7;20160101120000;
   * <br/>
   * TOTAL;1;
   * <br/>
   *
   * @param pathToFile путь до файла
   * @return результат парсинга
   * @throws IOException
   */
  public ParseResult parse(Path pathToFile) throws IOException {
    LOGGER.info("Parsing transactions");
    final var parseResult = new ParseResult();
    try (final var reader = Files.newBufferedReader(pathToFile, Charset.defaultCharset())) {
      long lineNumber = 1;
      String line = reader.readLine();

      if (line == null) {
        throw new EmptyFileException();
      }

      // Проверяем что хэдеры на месте
      final var headersPatternMatcher = HEADERS_PATTERN.matcher(line);
      if (headersPatternMatcher.matches()) {
        parseResult.setHeadersOk(true);
        lineNumber++;
      }

      // Парсим остальные строки
      while ((line = reader.readLine()) != null) {
        final var dataPatternMatcher = DATA_PATTERN.matcher(line);
        final var totalPatternMatcher = TOTAL_PATTERN.matcher(line);

        if (dataPatternMatcher.matches()) {
          // Записываем валидную транзакцию
          parseResult.addParseLineResult(new ParseLineResult(
            lineNumber,
            Integer.valueOf(dataPatternMatcher.group(1)),
            dataPatternMatcher.group(2),
            dataPatternMatcher.group(3))
          );
        } else if (totalPatternMatcher.matches()) {
          // Нашли TOTAL;... Парсим общее количество транзакций в файле
          parseResult.setRowsAmount(Long.valueOf(totalPatternMatcher.group(1)));
        } else {
          // Не смогли распознать строку
          parseResult.addUnparsedLine(lineNumber);
        }
        lineNumber++;
      }
    }

    return parseResult;
  }
}
