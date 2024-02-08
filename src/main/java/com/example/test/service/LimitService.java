package com.example.test.service;

import com.example.test.entity.LimitsEntity;
import com.example.test.enums.ExpenseCategory;

import java.math.BigDecimal;
import java.util.List;

public interface LimitService {
    List<LimitsEntity> getAll();

    List<LimitsEntity> getLimitsByAccountNumber(Long accountNumber);

    void setLimitByExpenseCategoryAndAccountNumber(ExpenseCategory expenseCategory, Long accountNumber, BigDecimal limit);
    void createLimitIfNotExist(Long accountNumber, ExpenseCategory expenseCategory);
}
