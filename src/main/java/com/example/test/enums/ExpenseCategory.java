package com.example.test.enums;

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

    public static ExpenseCategory fromString(String category) {
        for (ExpenseCategory ec : ExpenseCategory.values()) {
            if (ec.getCategory().equalsIgnoreCase(category)) {
                return ec;
            }
        }
        throw new IllegalArgumentException("Unknown category: " + category);
    }
}
