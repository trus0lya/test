package com.example.test.model.transaction;

import com.example.test.enums.Currency;
import com.example.test.enums.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionRequest {
    private Long accountNumFrom;
    private Long accountNumTo;
    private ExpenseCategory expenseCategory;
    private BigDecimal amount;
    private Currency currency;
}
