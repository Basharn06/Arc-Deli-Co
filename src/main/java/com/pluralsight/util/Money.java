package com.pluralsight.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

// money utils
public final class Money {
    private static final DecimalFormat DF = new DecimalFormat("0.00"); // 2 decimals
    private Money() {} // no ctor

    // format price
    public static String format(BigDecimal v) {
        if (v == null) return "0.00";
        return DF.format(v);
    }
}
