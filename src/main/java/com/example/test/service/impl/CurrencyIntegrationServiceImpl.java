package com.example.test.service.impl;

import com.example.test.model.exchangerate.ExchangeRateResponse;
import com.example.test.service.CurrencyIntegrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyIntegrationServiceImpl implements CurrencyIntegrationService {
    private final RestTemplate restTemplate;
    @Value("${apikey}")
    private String apiKey;

    @Value("${exchangeRateApiUrl}")
    private String exchangeRateApiUrl;

    @Autowired
    public CurrencyIntegrationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExchangeRateResponse getExchangeRate(String pair) {
        try {
            String url = String.format(exchangeRateApiUrl, pair, apiKey);
            String jsonString = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, ExchangeRateResponse.class);
        } catch (Exception e) {
            return null;
        }
    }
}
