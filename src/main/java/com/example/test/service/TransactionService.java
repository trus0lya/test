package com.example.test.service;

import com.example.test.entity.TransactionsEntity;
import com.example.test.enums.Currency;
import com.example.test.enums.ExpenseCategory;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    List<TransactionsEntity> getExceededLimits(Long accountNumber);

    void addTransaction(
            Long accountFrom, Long accountTo,
            ExpenseCategory category, BigDecimal amount,
            Currency currency
    );
}
