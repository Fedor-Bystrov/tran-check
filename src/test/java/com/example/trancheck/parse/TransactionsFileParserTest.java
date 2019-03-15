package com.example.trancheck.parse;

import com.example.trancheck.parse.pojo.ParseLineResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class TransactionsFileParserTest {
  private static final Path TX_1_CSV = Path.of("src/test/resources/test_tx_1.csv");

  @Test
  void parseTest() {
    final var transactionsFileParser = new TransactionsFileParser();
    try {
      final var parseResult = transactionsFileParser.parse(TX_1_CSV);
      assertTrue(parseResult.isHeadersOk());
      assertEquals(9, parseResult.getRowsAmount());
      assertEquals(5, parseResult.getParseLineResults().size());
      assertEquals(4, parseResult.getUnparsedLines().size());
      assertThat(parseResult.getParseLineResults(), containsInAnyOrder(
        new ParseLineResult(2, 123, "94.7", "20160101120000"),
        new ParseLineResult(3, 124, "150.75", "20160101120001"),
        new ParseLineResult(4, 125, "1020.2", "20160101120002"),
        new ParseLineResult(5, 126, "15.5", "20160101120003"),
        new ParseLineResult(6, 127, "120.74", "20160101120004")
      ));
      assertThat(parseResult.getUnparsedLines(), containsInAnyOrder(
        "128;;20160101120003;", ";;20160101120003;", ";;;", "ds"
      ));
    } catch (IOException e) {
      fail("File test_tx_1.csv is valid");
    }
  }
}
