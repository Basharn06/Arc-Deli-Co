package com.pluralsight.models;

import com.pluralsight.ui.UserInterface;

/**
 * Builder/helper methods for creating Drinks and Chips
 * using console prompts via UserInterface.
 */
public final class ExtrasBuilders {

    // private ctor
    private ExtrasBuilders() { } // utility-only class

    // build a drink
    public static Drink buildDrink(UserInterface ui) {
        ui.clear();
        ui.header("Add Drink");                          // screen title
        ui.info("Sizes: 1) SMALL  2) MEDIUM  3) LARGE"); // size prompt

        Drink.DSize size;                                // chosen size
        while (true) {
            int pick = ui.pick("Size:");
            if (pick == 1) { size = Drink.DSize.SMALL;  break; }
            if (pick == 2) { size = Drink.DSize.MEDIUM; break; }
            if (pick == 3) { size = Drink.DSize.LARGE;  break; }
            ui.warn("Pick 1, 2, or 3");                  // invalid input
        }

        String flavor = ui.ask("Flavor (e.g., cola, lemonade):"); // flavor input
        return new Drink(size, flavor);                            // new drink
    }

    // build chips
    public static Chips buildChips(UserInterface ui) {
        ui.clear();
        ui.header("Add Chips");                         // screen title
        String type = ui.ask("Type (e.g., BBQ, Plain, Sour Cream):"); // chip type
        return new Chips(type);                         // new chips
    }
}
