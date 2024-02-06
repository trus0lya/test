package com.example.test.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CurrencyConverter implements AttributeConverter<Currency, String> {

    @Override
    public String convertToDatabaseColumn(Currency attribute) {
        return attribute != null ? attribute.getCurrency() : null;
    }

    @Override
    public Currency convertToEntityAttribute(String dbData) {
        return dbData != null ? Currency.valueOf(dbData.toUpperCase()) : null;
    }
}