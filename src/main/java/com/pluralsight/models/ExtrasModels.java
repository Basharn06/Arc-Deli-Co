package com.pluralsight.models;

import com.pluralsight.util.PriceList;

import java.math.BigDecimal;

/**
 * Drink and Chips models.
 * Both implement PricedItem so Order can treat them the same.
 */

// drink item
public class Drink implements PricedItem {
    // drink sizes
    public enum DSize { SMALL, MEDIUM, LARGE }

    private final DSize size;    // drink size
    private final String flavor; // drink flavor

    // constructor
    public Drink(DSize size, String flavor) {
        this.size = size;
        this.flavor = flavor;
    }

    // short label
    @Override
    public String title() {
        return "Drink " + size + " - " + flavor; // for list display
    }

    // price logic
    @Override
    public BigDecimal price() {
        // map enum size to pricing string
        String key = switch (size) {
            case SMALL -> "small";
            case MEDIUM -> "medium";
            case LARGE -> "large";
        };
        return PriceList.drink(key); // use price table
    }

    // receipt line
    @Override
    public String receiptLine() {
        return "Drink: " + flavor + " (" + size + ")  $" + price(); // detailed output
    }
}

// chips item
public class Chips implements PricedItem {
    private final String type; // chip flavor/name

    // constructor
    public Chips(String type) {
        this.type = type;
    }

    // short label
    @Override
    public String title() {
        return "Chips - " + type; // for list display
    }

    // price logic
    @Override
    public BigDecimal price() {
        return PriceList.chips(); // flat price from table
    }

    // receipt line
    @Override
    public String receiptLine() {
        return "Chips: " + type + "  $" + price(); // detailed output
    }
}
