package com.example.trancheck.report.rule;

import com.example.trancheck.entity.Transaction;
import com.example.trancheck.parse.pojo.ParseLineResult;

import java.util.List;

public interface ValidationRule {
  void validate(ParseLineResult parseLineResult, List<Transaction> Transactions);
}
