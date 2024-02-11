package com.example.test.model.limit;

import com.example.test.enums.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class LimitRequest {
    private Long accountNumber;
    private ExpenseCategory expenseCategory;
    private BigDecimal limitUsd;
}
