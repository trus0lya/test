package com.example.test.model;

import com.example.test.enums.Currency;
import com.example.test.enums.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Transaction {
    private Long accountNumFrom;
    private Long accountNumTo;
    private ExpenseCategory expenseCategory;
    private BigDecimal amount;
    private Currency currency;
    private Timestamp dateTime;
    private Limit limit;
}
