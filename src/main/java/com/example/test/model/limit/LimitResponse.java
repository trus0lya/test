package com.example.test.model.limit;

import com.example.test.enums.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class LimitResponse {
    private Long accountNumber;
    private ExpenseCategory expenseCategory;
    private BigDecimal limitUsd;
    private Timestamp creationDate;
    private BigDecimal remainsBeforeExceed;
    private Timestamp updateDate;
}
