package com.pluralsight.models;

import com.pluralsight.ui.UserInterface;

// builds drinks/chips via UI (returns PricedItem)
public final class ExtrasBuilders {
    private ExtrasBuilders() { }

    public static PricedItem buildDrink(UserInterface ui) {
        ui.clear();
        ui.header("Add Drink");
        ui.info("Sizes:");
        ui.option("1) Small ($2.00)");
        ui.option("2) Medium ($2.50)");
        ui.option("3) Large ($3.00)");
        ui.line();

        int pick = ui.pick("Choose size:");
        Drink.DSize size;
        switch (pick) {
            case 1 -> size = Drink.DSize.SMALL;
            case 2 -> size = Drink.DSize.MEDIUM;
            case 3 -> size = Drink.DSize.LARGE;
            default -> { ui.warn("Defaulting to Small"); size = Drink.DSize.SMALL; }
        }
        String flavor = ui.ask("Flavor (e.g., Cola, Lemonade):");
        ui.ok(flavor + " added.");
        return new Drink(size, flavor); // package-private is fine (same package)
    }

    public static PricedItem buildChips(UserInterface ui) {
        ui.clear();
        ui.header("Add Chips");
        ui.info("Common: BBQ, Original, Sour Cream, Jalapeño");
        ui.line();
        String type = ui.ask("Chip flavor:");
        ui.ok(type + " chips added.");
        return new Chips(type); // package-private is fine (same package)
    }
}
