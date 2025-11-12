package com.pluralsight.models;

import java.math.BigDecimal;

/**
 * Common interface for anything that has a title and a price
 * (e.g., Sandwich, Drink, Chips).
 */
public interface PricedItem {
    String title();       // item name
    BigDecimal price();   // cost
    String receiptLine(); // receipt display
}
