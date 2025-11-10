package com.pluralsight.models;

import com.pluralsight.util.PriceList;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/* MENUMS & TOPPING CLASSES */

// sandwich sizes
enum Size {
    FOUR, EIGHT, TWELVE;                             // possible sizes
    public String label() {                           // readable text
        return switch (this) {
            case FOUR -> "4\"";
            case EIGHT -> "8\"";
            case TWELVE -> "12\"";
        };
    }
}

// bread options
enum Bread {
    WHITE, WHEAT, RYE, WRAP;
    public String label() {                           // readable text
        return switch (this) {
            case WHITE -> "white";
            case WHEAT -> "wheat";
            case RYE -> "rye";
            case WRAP -> "wrap";
        };
    }
}

// topping types
enum ToppingType { MEAT, CHEESE, REGULAR, SAUCE, SIDE }

// topping detail
class Topping {
    private final String name;                        // topping name
    private final ToppingType type;                   // topping category
    private int qty = 1;                              // 1=normal,2=extra

    // constructor
    public Topping(String name, ToppingType type) {
        this.name = name;
        this.type = type;
    }

    // mark as extra
    public void makeExtra() { this.qty = 2; }

    public String name() { return name; }
    public ToppingType type() { return type; }
    public int qty() { return qty; }
}

/*  SANDWICH CLASSES */

// base sandwich
public class Sandwich implements PricedItem {
    protected Size size;                 // size enum
    protected Bread bread;               // bread enum
    protected boolean toasted;           // toasted flag
    protected final List<Topping> tops = new ArrayList<>(); // toppings list

    // ctor
    public Sandwich(Size size, Bread bread, boolean toasted) {
        this.size = size; this.bread = bread; this.toasted = toasted;
    }

    // add topping
    public void addTop(Topping t) { tops.add(t); }

    // label
    @Override public String title() {
        return size.label() + " " + bread.label() + " sandwich";
    }

    // total price calc
    @Override public BigDecimal price() {
        BigDecimal total = PriceList.bread(size.label()); // base bread
        for (Topping t : tops) {
            switch (t.type()) {
                case MEAT -> {                              // meats priced
                    total = total.add(PriceList.meat(size.label()));
                    if (t.qty() > 1)
                        total = total.add(PriceList.extraMeat(size.label()));
                }
                case CHEESE -> {                            // cheeses priced
                    total = total.add(PriceList.cheese(size.label()));
                    if (t.qty() > 1)
                        total = total.add(PriceList.extraCheese(size.label()));
                }
                default -> {}                               // others free
            }
        }
        return total;
    }

    // receipt details
    @Override public String receiptLine() {
        String tlines = tops.stream()
                .map(t -> "   - " + t.type() + ": " + t.name() + (t.qty()>1 ? " (extra)" : ""))
                .collect(Collectors.joining("\n"));
        return """
               [%s] %s on %s
               %s
               Subtotal: $%s
               """.formatted(size.label(), toasted ? "Toasted" : "Classic",
                bread.label(), tlines, price());
    }
}

/*  SIGNATURE SANDWICHES (inherit) */

// BLT sandwich
class BLT extends Sandwich {
    public BLT(Size s, Bread b, boolean t) {
        super(s, b, t);
        addTop(new Topping("bacon", ToppingType.MEAT));
        addTop(new Topping("cheddar", ToppingType.CHEESE));
        addTop(new Topping("lettuce", ToppingType.REGULAR));
        addTop(new Topping("tomatoes", ToppingType.REGULAR));
        addTop(new Topping("ranch", ToppingType.SAUCE));
    }
    @Override public String title() { return "Signature: BLT (" + size.label() + ")"; }
}

// Philly cheesesteak
class PhillyCheesesteak extends Sandwich {
    public PhillyCheesesteak(Size s, Bread b, boolean t) {
        super(s, b, t);
        addTop(new Topping("steak", ToppingType.MEAT));
        addTop(new Topping("american", ToppingType.CHEESE));
        addTop(new Topping("peppers", ToppingType.REGULAR));
        addTop(new Topping("mayo", ToppingType.SAUCE));
    }
    @Override public String title() { return "Signature: Philly (" + size.label() + ")"; }
}
