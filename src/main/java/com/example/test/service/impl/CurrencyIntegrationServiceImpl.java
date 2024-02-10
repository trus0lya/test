package com.example.test.service.impl;

import com.example.test.model.ExchangeRateResponse;
import com.example.test.service.CurrencyIntegrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyIntegrationServiceImpl implements CurrencyIntegrationService {
    private final RestTemplate restTemplate;

    @Autowired
    public CurrencyIntegrationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExchangeRateResponse getExchangeRate(String url) {
        try {
            String jsonString = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, ExchangeRateResponse.class);
        } catch (Exception e) {
            return null;
        }
    }
}
