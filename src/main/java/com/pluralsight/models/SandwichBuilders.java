package com.pluralsight.models;

import com.pluralsight.ui.UserInterface;

import java.util.LinkedHashSet;
import java.util.Arrays;
import java.util.Set;

/**
 * Builds Sandwich objects by talking to the user
 * through the UserInterface (size, bread, toppings, etc).
 */
public final class SandwichBuilders {

    // utility class
    private SandwichBuilders() { } // prevent new

    // main builder flow
    public static Sandwich build(UserInterface ui) {
        ui.clear();
        ui.header("Add Sandwich");               // screen title

        Size size = askSize(ui);                 // choose size
        Bread bread = askBread(ui);              // choose bread

        // offer signature subs
        ui.line();
        ui.info("Signature Sandwiches:");
        ui.option("1) BLT");
        ui.option("2) Philly Cheesesteak");
        ui.option("0) Custom Build");
        int sig = ui.pick("Choose:");

        boolean toasted = askToast(ui);          // toasted or not

        Sandwich s = switch (sig) {
            case 1 -> new BLT(size, bread, toasted);              // BLT preset
            case 2 -> new PhillyCheesesteak(size, bread, toasted); // Philly preset
            default -> new Sandwich(size, bread, toasted);        // custom base
        };

        // only ask toppings for custom
        if (sig == 0) addToppings(ui, s);        // build custom

        return s;                                // finished sandwich
    }

    // ask for size
    private static Size askSize(UserInterface ui) {
        ui.line();
        ui.info("Sizes: 1) 4\"  2) 8\"  3) 12\"");
        while (true) {
            int pick = ui.pick("Size:");
            if (pick == 1) return Size.FOUR;
            if (pick == 2) return Size.EIGHT;
            if (pick == 3) return Size.TWELVE;
            ui.warn("Pick 1, 2, or 3");          // invalid size
        }
    }

    // ask for bread
    private static Bread askBread(UserInterface ui) {
        ui.line();
        ui.info("Bread: 1) white  2) wheat  3) rye  4) wrap");
        while (true) {
            int pick = ui.pick("Bread:");
            return switch (pick) {
                case 1 -> Bread.WHITE;
                case 2 -> Bread.WHEAT;
                case 3 -> Bread.RYE;
                case 4 -> Bread.WRAP;
                default -> { ui.warn("Pick 1-4"); yield null; }
            };
        }
    }

    // ask toasted
    private static boolean askToast(UserInterface ui) {
        ui.line();
        ui.info("Toasted? 1) Yes  0) No");
        int pick = ui.pick("Toasted:");
        return pick == 1;                        // true if yes
    }

    // toppings flow
    private static void addToppings(UserInterface ui, Sandwich s) {
        // meats (premium)
        Set<String> meats = new LinkedHashSet<>(Arrays.asList(
                "steak","ham","salami","roast beef","chicken","bacon"
        ));

        // cheeses (premium)
        Set<String> cheeses = new LinkedHashSet<>(Arrays.asList(
                "american","provolone","cheddar","swiss"
        ));

        // regular toppings (included)
        Set<String> regs = new LinkedHashSet<>(Arrays.asList(
                "lettuce","peppers","onions","tomatoes","jalapeños",
                "cucumbers","pickles","guacamole","mushrooms"
        ));

        // sauces (included)
        Set<String> sauces = new LinkedHashSet<>(Arrays.asList(
                "mayo","mustard","ketchup","ranch","thousand island","vinaigrette"
        ));

        // sides (included)
        Set<String> sides = new LinkedHashSet<>(Arrays.asList(
                "au jus","sauce"
        ));

        // each category in order
        loopCategory(ui,"Meats", meats, s, ToppingType.MEAT, true);
        loopCategory(ui,"Cheeses", cheeses, s, ToppingType.CHEESE, true);
        loopCategory(ui,"Toppings", regs, s, ToppingType.REGULAR, false);
        loopCategory(ui,"Sauces", sauces, s, ToppingType.SAUCE, false);
        loopCategory(ui,"Sides", sides, s, ToppingType.SIDE, false);
    }

    // category loop helper
    private static void loopCategory(
            UserInterface ui,
            String label,
            Set<String> options,
            Sandwich s,
            ToppingType type,
            boolean allowExtra
    ) {
        ui.line();
        ui.info(label + " (type 'done' to finish)");
        ui.info(String.join(", ", options));      // show choices

        while (true) {
            String pick = ui.ask("Add:");
            if (pick.equalsIgnoreCase("done")) break; // finish category

            String normalized = pick.toLowerCase();
            if (!options.contains(normalized)) {
                ui.warn("Unknown option. Try again."); // not found
                continue;
            }

            Topping t = new Topping(normalized, type); // new topping

            if (allowExtra) {
                String extra = ui.ask("Extra? (y/n):"); // extra prompt
                if (extra.equalsIgnoreCase("y")) t.makeExtra();
            }

            s.addTop(t);                           // attach topping
            ui.ok(pick + " added");               // confirmation
        }
    }
}
