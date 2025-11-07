package com.pluralsight.models;

import java.math.BigDecimal;
import java.util.*;

// common item type
public interface PricedItem {
    String title();       // short label
    BigDecimal price();   // numeric cost
    String receiptLine(); // detailed line
}

// order holds items
public class Order {
    private final List<PricedItem> items = new ArrayList<>(); // order list

    // add new item
    public void addItem(PricedItem item) { items.add(item); }

    // empty check
    public boolean isEmpty() { return items.isEmpty(); }

    // newest first for display
    public List<PricedItem> itemsNewestFirst() {
        var copy = new ArrayList<>(items);
        Collections.reverse(copy);
        return copy;
    }

    // total price
    public BigDecimal total() {
        BigDecimal sum = BigDecimal.ZERO;
        for (var it : items) sum = sum.add(it.price());
        return sum;
    }

    // all items (read-only)
    public List<PricedItem> items() { return Collections.unmodifiableList(items); }
}
