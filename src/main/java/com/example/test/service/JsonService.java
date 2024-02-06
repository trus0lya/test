package com.example.test.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JsonService {

    private final RestTemplate restTemplate;

    public JsonService() {
        this.restTemplate = new RestTemplate();
    }

    public <T> T getJsonObjectFromUrl(String url, Class<T> responseType) {
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