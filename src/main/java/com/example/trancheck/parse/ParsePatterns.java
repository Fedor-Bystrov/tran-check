package com.example.trancheck.parse;

import java.util.regex.Pattern;

public final class ParsePatterns {
  public static final Pattern HEADERS_PATTERN = Pattern.compile("^PID;PAMOUNT;PDATA;$");
  // Считаем что отрицательных amount нет
  public static final Pattern DATA_PATTERN = Pattern.compile("^(\\d+);(\\d+\\.\\d+);(.*);$");
  public static final Pattern TOTAL_PATTERN = Pattern.compile("^TOTAL;(\\d+);$");
}
