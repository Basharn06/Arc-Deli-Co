package com.pluralsight.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ------------ Size enum ------------
enum Size {
    FOUR("4\"", 4),
    EIGHT("8\"", 8),
    TWELVE("12\"", 12);

    private final String label;
    private final int inches;

    Size(String label, int inches) {
        this.label = label;
        this.inches = inches;
    }

    public String label() {
        return label;
    }

    public int inches() {
        return inches;
    }

    public static Size fromInt(int value) {
        return switch (value) {
            case 4 -> FOUR;
            case 8 -> EIGHT;
            case 12 -> TWELVE;
            default -> throw new IllegalArgumentException("Invalid size: " + value);
        };
    }
}

// ------------ Bread enum ------------
enum Bread {
    WHITE("white"),
    WHEAT("wheat"),
    RYE("rye"),
    WRAP("wrap");

    private final String label;

    Bread(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static Bread fromInt(int choice) {
        return switch (choice) {
            case 1 -> WHITE;
            case 2 -> WHEAT;
            case 3 -> RYE;
            case 4 -> WRAP;
            default -> throw new IllegalArgumentException("Invalid bread choice: " + choice);
        };
    }
}

// ------------ ToppingType enum ------------
enum ToppingType {
    MEAT,
    CHEESE,
    REGULAR,
    SAUCE,
    SIDE
}

// ------------ Topping class ------------
class Topping {
    private final String name;
    private final ToppingType type;
    private final boolean extra;

    public Topping(String name, ToppingType type, boolean extra) {
        this.name = name;
        this.type = type;
        this.extra = extra;
    }

    public String getName() {
        return name;
    }

    public ToppingType getType() {
        return type;
    }

    public boolean isExtra() {
        return extra;
    }
}

// ------------ Sandwich base class (implements PricedItem) ------------
class Sandwich implements PricedItem {

    private final Size size;
    private final Bread bread;
    private final boolean toasted;
    private final List<Topping> toppings = new ArrayList<>();

    public Sandwich(Size size, Bread bread, boolean toasted) {
        this.size = size;
        this.bread = bread;
        this.toasted = toasted;
    }

    // ----- configuration -----
    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    public List<Topping> getToppings() {
        return Collections.unmodifiableList(toppings);
    }

    public Size getSize() {
        return size;
    }

    public Bread getBread() {
        return bread;
    }

    public boolean isToasted() {
        return toasted;
    }

    // ----- PricedItem methods -----

    @Override
    public String title() {
        return size.label() + " Classic on " + bread.label();
    }

    // total sandwich price (base + all toppings)
    @Override
    public BigDecimal price() {
        BigDecimal total = basePrice(); // base price by size
        for (Topping topping : toppings) {
            total = total.add(toppingPrice(topping));
        }
        return total;
    }

    @Override
    public String receiptLine() {
        StringBuilder sb = new StringBuilder();

        // sandwich header
        sb.append("[")
                .append(size.label())
                .append("] Classic on ")
                .append(bread.label());
        if (toasted) {
            sb.append(" (toasted)");
        }
        sb.append("\n");

        // topping lines with individual prices
        for (Topping topping : toppings) {
            String typeLabel = topping.getType().name() + ":"; // e.g. MEAT:
            String nameLabel = topping.getName() + (topping.isExtra() ? " (extra)" : "");
            String priceLabel = formatMoney(toppingPrice(topping));

            sb.append(String.format("  - %-8s %-18s %6s%n",
                    typeLabel,
                    nameLabel,
                    priceLabel));
        }

        // subtotal for this sandwich
        sb.append(String.format("Subtotal: %s%n", formatMoney(price())));

        return sb.toString();
    }

    // ----- pricing helpers -----

    // base sandwich price by size (from your spec)
    private BigDecimal basePrice() {
        return switch (size) {
            case FOUR -> new BigDecimal("5.50");
            case EIGHT -> new BigDecimal("7.00");
            case TWELVE -> new BigDecimal("8.50");
        };
    }

    // price for a single topping based on type + size + extra flag
    private BigDecimal toppingPrice(Topping topping) {
        switch (topping.getType()) {
            case MEAT -> {
                if (topping.isExtra()) {
                    return switch (size) {
                        case FOUR -> new BigDecimal("0.50");
                        case EIGHT -> new BigDecimal("1.00");
                        case TWELVE -> new BigDecimal("1.50");
                    };
                } else {
                    return switch (size) {
                        case FOUR -> new BigDecimal("1.00");
                        case EIGHT -> new BigDecimal("2.00");
                        case TWELVE -> new BigDecimal("3.00");
                    };
                }
            }
            case CHEESE -> {
                if (topping.isExtra()) {
                    return switch (size) {
                        case FOUR -> new BigDecimal("0.30");
                        case EIGHT -> new BigDecimal("0.60");
                        case TWELVE -> new BigDecimal("0.90");
                    };
                } else {
                    return switch (size) {
                        case FOUR -> new BigDecimal("0.75");
                        case EIGHT -> new BigDecimal("1.50");
                        case TWELVE -> new BigDecimal("2.25");
                    };
                }
            }
            case REGULAR, SAUCE, SIDE -> {
                // regular toppings & sauces are free
                return BigDecimal.ZERO;
            }
            default -> {
                return BigDecimal.ZERO;
            }
        }
    }

    private String formatMoney(BigDecimal value) {
        return String.format("$%.2f", value);
    }
}

// ------------ Signature sandwiches ------------

class BLT extends Sandwich {
    public BLT(Size size, Bread bread, boolean toasted) {
        super(size, bread, toasted);
        addTopping(new Topping("bacon", ToppingType.MEAT, false));
        addTopping(new Topping("lettuce", ToppingType.REGULAR, false));
        addTopping(new Topping("tomatoes", ToppingType.REGULAR, false));
        addTopping(new Topping("mayo", ToppingType.SAUCE, false));
    }

    @Override
    public String title() {
        return getSize().label() + " BLT on " + getBread().label();
    }
}

class PhillyCheesesteak extends Sandwich {
    public PhillyCheesesteak(Size size, Bread bread, boolean toasted) {
        super(size, bread, toasted);
        addTopping(new Topping("steak", ToppingType.MEAT, false));
        addTopping(new Topping("provolone", ToppingType.CHEESE, false));
        addTopping(new Topping("onions", ToppingType.REGULAR, false));
        addTopping(new Topping("peppers", ToppingType.REGULAR, false));
    }

    @Override
    public String title() {
        return getSize().label() + " Philly on " + getBread().label();
    }
}

class RoastBeefMelt extends Sandwich {
    public RoastBeefMelt(Size size, Bread bread, boolean toasted) {
        super(size, bread, toasted);
        addTopping(new Topping("roast beef", ToppingType.MEAT, false));
        addTopping(new Topping("cheddar", ToppingType.CHEESE, false));
        addTopping(new Topping("onions", ToppingType.REGULAR, false));
        addTopping(new Topping("mustard", ToppingType.SAUCE, false));
    }

    @Override
    public String title() {
        return getSize().label() + " Roast Beef Melt on " + getBread().label();
    }
}

class VeggieDeluxe extends Sandwich {
    public VeggieDeluxe(Size size, Bread bread, boolean toasted) {
        super(size, bread, toasted);
        addTopping(new Topping("lettuce", ToppingType.REGULAR, false));
        addTopping(new Topping("tomatoes", ToppingType.REGULAR, false));
        addTopping(new Topping("cucumbers", ToppingType.REGULAR, false));
        addTopping(new Topping("peppers", ToppingType.REGULAR, false));
        addTopping(new Topping("vinaigrette", ToppingType.SAUCE, false));
    }

    @Override
    public String title() {
        return getSize().label() + " Veggie Deluxe on " + getBread().label();
    }
}
