package com.example.test.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ExpenseCategory {
    PRODUCT("product"),
    SERVICE("service");

    private final String category;

    ExpenseCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @JsonCreator
    public static ExpenseCategory forValue(String value) {
        for (ExpenseCategory category : ExpenseCategory.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }
        return null; // or throw an exception
    }

}
