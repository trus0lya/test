package com.example.test.model.limit;

import com.example.test.enums.ExpenseCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp creationDate;
    private BigDecimal remainsBeforeExceed;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateDate;
}
