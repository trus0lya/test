package com.example.test.model;

import com.example.test.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
public class ExchangeRate {
    private Currency currencyFrom;
    private Currency currencyTo;
    private BigDecimal rate;
    private Timestamp dateOfUpdate;
}