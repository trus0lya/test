package com.example.test.entity;

import com.example.test.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ExchangeRateEntityPK implements Serializable {
    private Currency currencyFrom;
    private Currency currencyTo;
}
