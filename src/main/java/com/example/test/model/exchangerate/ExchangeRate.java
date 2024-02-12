package com.example.test.model.exchangerate;

import com.example.test.enums.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dateOfUpdate;
}