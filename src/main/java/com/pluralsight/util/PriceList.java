package com.pluralsight.util;

import java.math.BigDecimal;

// Price table for all products
public final class PriceList {
    private PriceList() {} // utility only

    // base sandwich price
    public static BigDecimal bread(String size) {
        return switch (size) {
            case "4" -> bd(5.50);
            case "8" -> bd(7.00);
            case "12" -> bd(8.50);
            default -> bd(0);
        };
    }

    // meat add-on
    public static BigDecimal meat(String size) {
        return switch (size) {
            case "4" -> bd(1.00);
            case "8" -> bd(2.00);
            case "12" -> bd(3.00);
            default -> bd(0);
        };
    }

    // extra meat add-on
    public static BigDecimal extraMeat(String size) {
        return switch (size) {
            case "4" -> bd(0.50);
            case "8" -> bd(1.00);
            case "12" -> bd(1.50);
            default -> bd(0);
        };
    }

    // cheese add-on
    public static BigDecimal cheese(String size) {
        return switch (size) {
            case "4" -> bd(0.75);
            case "8" -> bd(1.50);
            case "12" -> bd(2.25);
            default -> bd(0);
        };
    }

    // extra cheese add-on
    public static BigDecimal extraCheese(String size) {
        return switch (size) {
            case "4" -> bd(0.30);
            case "8" -> bd(0.60);
            case "12" -> bd(0.90);
            default -> bd(0);
        };
    }

    // drink prices
    public static BigDecimal drink(String size) {
        return switch (size.toLowerCase()) {
            case "small" -> bd(2.00);
            case "medium" -> bd(2.50);
            case "large" -> bd(3.00);
            default -> bd(0);
        };
    }

    // chips
    public static BigDecimal chips() { return bd(1.50); }

    // helper: format decimals
    private static BigDecimal bd(double v) { return new BigDecimal(String.format("%.2f", v)); }
}
