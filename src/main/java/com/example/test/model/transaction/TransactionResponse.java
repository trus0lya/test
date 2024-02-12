package com.example.test.model.transaction;

import com.example.test.enums.Currency;
import com.example.test.enums.ExpenseCategory;
import com.example.test.model.limit.LimitResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private Long accountNumFrom;
    private Long accountNumTo;
    private ExpenseCategory expenseCategory;
    private BigDecimal amount;
    private Currency currency;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dateTime;
    private LimitResponse limit;
}
