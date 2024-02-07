package com.example.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

public class TwelveDataClient {

    private final RestTemplate restTemplate;

    public TwelveDataClient() {
        this.restTemplate = new RestTemplate();
    }

    public <T> T getExchangeRate(String url, Class<T> responseType) {
        try {
            String jsonString = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, responseType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
