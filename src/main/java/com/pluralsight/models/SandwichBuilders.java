package com.pluralsight.models;

import com.pluralsight.ui.UserInterface;

import java.util.LinkedHashSet;
import java.util.Arrays;
import java.util.Set;

// builds sandwiches via UI (returns PricedItem)
public final class SandwichBuilders {
    private SandwichBuilders() { }

    // return PricedItem to avoid exposing package-private Sandwich
    public static PricedItem build(UserInterface ui) {
        ui.clear();
        ui.header("Add Sandwich");

        Size size = askSize(ui);
        Bread bread = askBread(ui);

        ui.line();
        ui.info("Signature Sandwiches:");
        ui.option("1) BLT");
        ui.option("2) Philly Cheesesteak");
        ui.option("3) Roast Beef Melt");
        ui.option("4) Veggie Deluxe");
        ui.option("0) Custom Build");
        int sig = ui.pick("Choose:");

        boolean toasted = askToast(ui);

        Sandwich s = switch (sig) {
            case 1 -> new BLT(size, bread, toasted);
            case 2 -> new PhillyCheesesteak(size, bread, toasted);
            case 3 -> new RoastBeefMelt(size, bread, toasted);
            case 4 -> new VeggieDeluxe(size, bread, toasted);
            default -> new Sandwich(size, bread, toasted);
        };

        if (sig == 0) addToppings(ui, s);
        return s; // as PricedItem
    }

    private static Size askSize(UserInterface ui) {
        ui.line(); ui.info("Sizes: 1) 4\"  2) 8\"  3) 12\"");
        while (true) {
            int p = ui.pick("Size:");
            if (p == 1) return Size.FOUR;
            if (p == 2) return Size.EIGHT;
            if (p == 3) return Size.TWELVE;
            ui.warn("Pick 1-3");
        }
    }

    private static Bread askBread(UserInterface ui) {
        ui.line(); ui.info("Bread: 1) white  2) wheat  3) rye  4) wrap");
        while (true) {
            int p = ui.pick("Bread:");
            return switch (p) {
                case 1 -> Bread.WHITE;
                case 2 -> Bread.WHEAT;
                case 3 -> Bread.RYE;
                case 4 -> Bread.WRAP;
                default -> { ui.warn("Pick 1-4"); yield null; }
            };
        }
    }

    private static boolean askToast(UserInterface ui) {
        ui.line(); ui.info("Toasted? 1) Yes  0) No");
        return ui.pick("Toasted:") == 1;
    }

    private static void addToppings(UserInterface ui, Sandwich s) {
        Set<String> meats = new LinkedHashSet<>(Arrays.asList(
                "steak","ham","salami","roast beef","chicken","bacon"));
        Set<String> cheeses = new LinkedHashSet<>(Arrays.asList(
                "american","provolone","cheddar","swiss"));
        Set<String> regs = new LinkedHashSet<>(Arrays.asList(
                "lettuce","peppers","onions","tomatoes","jalapeños","cucumbers","pickles","guacamole","mushrooms"));
        Set<String> sauces = new LinkedHashSet<>(Arrays.asList(
                "mayo","mustard","ketchup","ranch","thousand island","vinaigrette"));
        Set<String> sides = new LinkedHashSet<>(Arrays.asList("au jus","sauce"));

        loopCategory(ui,"Meats", meats, s, ToppingType.MEAT, true);
        loopCategory(ui,"Cheeses", cheeses, s, ToppingType.CHEESE, true);
        loopCategory(ui,"Toppings", regs, s, ToppingType.REGULAR, false);
        loopCategory(ui,"Sauces", sauces, s, ToppingType.SAUCE, false);
        loopCategory(ui,"Sides", sides, s, ToppingType.SIDE, false);
    }

    private static void loopCategory(UserInterface ui, String label, Set<String> options,
                                     Sandwich s, ToppingType type, boolean allowExtra) {
        ui.line(); ui.info(label + " (type 'done' to finish)");
        ui.info(String.join(", ", options));
        while (true) {
            String pick = ui.ask("Add:");
            if (pick.equalsIgnoreCase("done")) break;
            String norm = pick.toLowerCase();
            if (!options.contains(norm)) { ui.warn("Unknown. Try again."); continue; }
            Topping t = new Topping(norm, type);
            if (allowExtra && ui.ask("Extra? (y/n):").equalsIgnoreCase("y")) t.makeExtra();
            s.addTop(t); ui.ok(pick + " added");
        }
    }
}
