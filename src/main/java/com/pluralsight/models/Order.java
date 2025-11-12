package com.pluralsight.models;

import java.math.BigDecimal;
import java.util.*;

/**
 * Holds all order items (sandwiches, drinks, chips).
 */
public class Order {
    private final List<PricedItem> items = new ArrayList<>(); // order list

    // add item to order
    public void addItem(PricedItem item) { items.add(item); }

    // check empty
    public boolean isEmpty() { return items.isEmpty(); }

    // check if order has at least one sandwich
    public boolean hasSandwich() {
        return items.stream().anyMatch(i -> i instanceof Sandwich);
    }

    // check if order has any drink/chips
    public boolean hasNonSandwich() {
        return items.stream().anyMatch(i -> (i instanceof Drink) || (i instanceof Chips));
    }

    // newest-first copy
    public List<PricedItem> itemsNewestFirst() {
        List<PricedItem> copy = new ArrayList<>(items);
        Collections.reverse(copy);
        return copy;
    }

    // get total price
    public BigDecimal total() {
        BigDecimal sum = BigDecimal.ZERO;
        for (var it : items) sum = sum.add(it.price());
        return sum;
    }

    // read-only list
    public List<PricedItem> items() { return Collections.unmodifiableList(items); }
}
