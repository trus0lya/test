package com.example.test.model;

import com.example.test.enums.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Limit {
    private Long accountNumber;
    private ExpenseCategory expenseCategory;
    private BigDecimal limitUsd;
    private Timestamp creationDate;
    private BigDecimal remainsBeforeExceed;
    private Timestamp updateDate;
}
