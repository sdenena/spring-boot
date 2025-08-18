package com.example.springdemo.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountType {
    CHECKING("CHECKING"),
    SAVINGS("SAVINGS"),
    FIXED("FIXED");

    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static AccountType fromValue(String value) {
        for (AccountType type : AccountType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid AccountType: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
