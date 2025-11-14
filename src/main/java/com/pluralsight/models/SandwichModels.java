package com.pluralsight.models;

import com.pluralsight.util.PriceList;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/* ───────────  ENUMS / SUPPORT TYPES  ─────────── */
enum Size {
    FOUR, EIGHT, TWELVE;
    String label() {
        return switch (this) {
            case FOUR -> "4\"";
            case EIGHT -> "8\"";
            case TWELVE -> "12\"";
        };
    }
}
enum Bread {
    WHITE, WHEAT, RYE, WRAP;
    String label() {
        return switch (this) {
            case WHITE -> "white";
            case WHEAT -> "wheat";
            case RYE -> "rye";
            case WRAP -> "wrap";
        };
    }
}
enum ToppingType { MEAT, CHEESE, REGULAR, SAUCE, SIDE }

class Topping {
    private final String name;
    private final ToppingType type;
    private int qty = 1;

    Topping(String name, ToppingType type) { this.name = name; this.type = type; }
    void makeExtra() { qty = 2; }
    String name() { return name; }
    ToppingType type() { return type; }
    int qty() { return qty; }
}

/* ───────────  SANDWICH + SIGNATURES  ─────────── */
class Sandwich implements PricedItem {
    protected Size size;
    protected Bread bread;
    protected boolean toasted;
    protected final List<Topping> tops = new ArrayList<>();

    Sandwich(Size size, Bread bread, boolean toasted) {
        this.size = size; this.bread = bread; this.toasted = toasted;
    }

    void addTop(Topping t) { tops.add(t); }

    @Override public String title() { return size.label() + " " + bread.label() + " sandwich"; }

    @Override public BigDecimal price() {
        // map enum to PriceList keys ("4","8","12")  // size key
        String key = switch (size) { case FOUR -> "4"; case EIGHT -> "8"; case TWELVE -> "12"; };

        BigDecimal total = PriceList.bread(key);        // base bread price
        for (Topping t : tops) {
            switch (t.type()) {
                case MEAT -> {
                    total = total.add(PriceList.meat(key));
                    if (t.qty() > 1) total = total.add(PriceList.extraMeat(key));
                }
                case CHEESE -> {
                    total = total.add(PriceList.cheese(key));
                    if (t.qty() > 1) total = total.add(PriceList.extraCheese(key));
                }
                default -> { /* included */ }
            }
        }
        return total;
    }

    @Override public String receiptLine() {
        String tlines = tops.stream()
                .map(t -> "   - " + t.type() + ": " + t.name() + (t.qty() > 1 ? " (extra)" : ""))
                .collect(Collectors.joining("\n"));
        return """
               [%s] %s on %s
               %s
               Subtotal: $%s
               """.formatted(size.label(), toasted ? "Toasted" : "Classic",
                bread.label(), tlines, price());
    }
}

class BLT extends Sandwich {
    BLT(Size s, Bread b, boolean t) {
        super(s, b, t);
        addTop(new Topping("bacon", ToppingType.MEAT));
        addTop(new Topping("cheddar", ToppingType.CHEESE));
        addTop(new Topping("lettuce", ToppingType.REGULAR));
        addTop(new Topping("tomatoes", ToppingType.REGULAR));
        addTop(new Topping("ranch", ToppingType.SAUCE));
    }
    @Override public String title() { return "Signature: BLT (" + size.label() + ")"; }
}
class PhillyCheesesteak extends Sandwich {
    PhillyCheesesteak(Size s, Bread b, boolean t) {
        super(s, b, t);
        addTop(new Topping("steak", ToppingType.MEAT));
        addTop(new Topping("american", ToppingType.CHEESE));
        addTop(new Topping("peppers", ToppingType.REGULAR));
        addTop(new Topping("mayo", ToppingType.SAUCE));
    }
    @Override public String title() { return "Signature: Philly (" + size.label() + ")"; }
}
class RoastBeefMelt extends Sandwich {
    RoastBeefMelt(Size s, Bread b, boolean t) {
        super(s, b, t);
        addTop(new Topping("roast beef", ToppingType.MEAT));
        addTop(new Topping("swiss", ToppingType.CHEESE));
        addTop(new Topping("onions", ToppingType.REGULAR));
        addTop(new Topping("mustard", ToppingType.SAUCE));
    }
    @Override public String title() { return "Signature: Roast Beef Melt (" + size.label() + ")"; }
}
class VeggieDeluxe extends Sandwich {
    VeggieDeluxe(Size s, Bread b, boolean t) {
        super(s, b, t);
        addTop(new Topping("provolone", ToppingType.CHEESE));
        addTop(new Topping("lettuce", ToppingType.REGULAR));
        addTop(new Topping("tomatoes", ToppingType.REGULAR));
        addTop(new Topping("cucumbers", ToppingType.REGULAR));
        addTop(new Topping("pickles", ToppingType.REGULAR));
        addTop(new Topping("mushrooms", ToppingType.REGULAR));
        addTop(new Topping("vinaigrette", ToppingType.SAUCE));
    }
    @Override public String title() { return "Signature: Veggie Deluxe (" + size.label() + ")"; }
}
