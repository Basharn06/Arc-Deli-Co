package com.pluralsight.models;

import java.math.BigDecimal;
import com.pluralsight.util.PriceList;

// drink model
public class Drink implements PricedItem {
    public enum DSize { SMALL, MEDIUM, LARGE }

    private final DSize size;      // drink size
    private final String flavor;   // drink name

    // constructor
    public Drink(DSize size, String flavor) {
        this.size = size;
        this.flavor = flavor;
    }

    // title text
    @Override
    public String title() {
        return flavor + " (" + size.name().toLowerCase() + ")";
    }

    // price from PriceList
    @Override
    public BigDecimal price() {
        return switch (size) {
            case SMALL -> PriceList.drink("Small");
            case MEDIUM -> PriceList.drink("Medium");
            case LARGE -> PriceList.drink("Large");
        };
    }

    // receipt text
    @Override
    public String receiptLine() {
        return String.format("%s (%s)  $%s", flavor, size.name(), price());
    }
}
