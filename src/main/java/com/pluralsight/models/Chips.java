package com.pluralsight.models;

import java.math.BigDecimal;
import com.pluralsight.util.PriceList;

// chips model
public class Chips implements PricedItem {
    private final String type;  // chip flavor

    // constructor
    public Chips(String type) {
        this.type = type;
    }

    // label
    @Override
    public String title() { return type + " chips"; }

    // price
    @Override
    public BigDecimal price() { return PriceList.chips(); }

    // receipt line
    @Override
    public String receiptLine() {
        return String.format("%s chips  $%s", type, price());
    }
}
