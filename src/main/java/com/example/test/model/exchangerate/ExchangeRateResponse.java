package com.example.test.model.exchangerate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class ExchangeRateResponse {
    private String symbol;
    private double rate;
    private long timestamp;
}
