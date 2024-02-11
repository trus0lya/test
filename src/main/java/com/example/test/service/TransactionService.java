package com.example.test.service;

import com.example.test.entity.TransactionsEntity;
import com.example.test.enums.Currency;
import com.example.test.enums.ExpenseCategory;
import com.example.test.model.transaction.TransactionRequest;
import com.example.test.model.transaction.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    List<TransactionResponse> getExceededLimits(Long accountNumber);

    void addTransaction(TransactionRequest transactionRequest);
}
