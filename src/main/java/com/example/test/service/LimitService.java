package com.example.test.service;

import com.example.test.enums.ExpenseCategory;
import com.example.test.model.limit.LimitResponse;

import java.math.BigDecimal;
import java.util.List;

public interface LimitService {
    List<LimitResponse> getAll();
    List<LimitResponse> getLimitsByAccountNumber(Long accountNumber);
    void setLimitByExpenseCategoryAndAccountNumber(ExpenseCategory expenseCategory, Long accountNumber, BigDecimal limit);
}
