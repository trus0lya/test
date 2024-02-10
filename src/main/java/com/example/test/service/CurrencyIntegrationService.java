package com.example.test.service;

import com.example.test.model.ExchangeRateResponse;

public interface CurrencyIntegrationService {
    ExchangeRateResponse getExchangeRate(String url);
}
