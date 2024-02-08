package com.example.test.util;

import com.example.test.model.ExchangeRateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

public class TwelveDataUtil {
    public static ExchangeRateResponse getExchangeRate(String url) {
        try {
            String jsonString = new RestTemplate().getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, ExchangeRateResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}