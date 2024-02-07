package com.example.test.enums;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ExpenseCategoryConverter implements AttributeConverter<ExpenseCategory, String> {

    @Override
    public String convertToDatabaseColumn(ExpenseCategory attribute) {
        return attribute != null ? attribute.getCategory() : null;
    }

    @Override
    public ExpenseCategory convertToEntityAttribute(String dbData) {
        return dbData != null ? ExpenseCategory.valueOf(dbData.toUpperCase()) : null;
    }
}