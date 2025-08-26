package com.example.springdemo.utils;

import java.text.DecimalFormat;
import java.util.UUID;

public class Generator {
    // Format: ACC-0000000001
    private static final DecimalFormat formatter = new DecimalFormat("0000000000");
    public static final String REQUEST_UUID = UUID.randomUUID().toString();

    public static String generateAccountNumber(Long number) {
        return "ACC-" + formatter.format(number);
    }
}
