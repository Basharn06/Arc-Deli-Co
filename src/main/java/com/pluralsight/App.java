package com.pluralsight;

import com.pluralsight.models.*;
import com.pluralsight.ui.UserInterface;

import java.util.List;

// app controller
public class App {
    private final UserInterface ui = new UserInterface(); // console io

    // run loop
    public void run() {
        while (true) {
            // home screen
            ui.clear();                                // clear view
            ui.header("Arc Deli & Co.");               // brand
            ui.sub("Crafted Subs • Toasted • Fresh");  // tagline
            ui.line();
            ui.option("1) New Order");
            ui.option("0) Exit");
            int pick = ui.pick("Choose:");
            if (pick == 1) handleOrder();              // go to order
            else if (pick == 0) { ui.ok("Goodbye!"); break; }
            else { ui.warn("Invalid choice"); ui.pause(); }
        }
    }

    // order flow
    private void handleOrder() {
        Order order = new Order(); // new basket
        while (true) {
            ui.clear();
            ui.header("Order Station");                // order screen
            ui.sub("Newest items show first");         // hint
            ui.line();
            ui.option("1) Add Sandwich");
            ui.option("2) Add Drink");
            ui.option("3) Add Chips");
            ui.option("4) Checkout");
            ui.option("0) Cancel Order");
            ui.line();
            printOrderList(order);                     // show items
            int pick = ui.pick("Choose:");
            switch (pick) {
                case 1 -> { addSandwich(order); }
                case 2 -> { addDrink(order); }
                case 3 -> { addChips(order); }
                case 4 -> { checkoutPreview(order); }
                case 0 -> { ui.warn("Order canceled."); ui.pause(); return; }
                default -> { ui.warn("Invalid choice"); ui.pause(); }
            }
        }
    }

    // add sandwich
    private void addSandwich(Order order) {
        Sandwich s = SandwichBuilders.build(ui); // build via UI
        order.addItem(s);                        // add to cart
        ui.ok("Sandwich added.");
        ui.pause();
    }

    // add drink
    private void addDrink(Order order) {
        Drink d = ExtrasBuilders.buildDrink(ui); // build via UI
        order.addItem(d);                        // add to cart
        ui.ok("Drink added.");
        ui.pause();
    }

    // add chips
    private void addChips(Order order) {
        Chips c = ExtrasBuilders.buildChips(ui); // build via UI
        order.addItem(c);                        // add to cart
        ui.ok("Chips added.");
        ui.pause();
    }

    // show items list
    private void printOrderList(Order order) {
        List<PricedItem> items = order.itemsNewestFirst(); // newest first
        if (items.isEmpty()) { ui.info("No items yet."); return; }
        int idx = items.size();
        for (PricedItem it : items) {
            System.out.printf("%2d) %s  —  $%s%n", idx--, it.title(), it.price());
        }
        System.out.printf("Total: $%s%n", order.total());
    }

    // checkout preview (no save yet)
    private void checkoutPreview(Order order) {
        if (order.isEmpty()) {                   // empty guard
            ui.warn("Order is empty. Add items first.");
            ui.pause();
            return;
        }
        ui.clear();
        ui.header("Checkout");
        ui.line();
        // details
        for (PricedItem it : order.itemsNewestFirst()) {
            System.out.println(it.receiptLine());
            System.out.println();
        }
        System.out.println("TOTAL: $" + order.total());
        ui.line();
        ui.info("Receipt save coming next…");    // placeholder
        ui.pause();
    }
}
