package com.pluralsight;

import com.pluralsight.models.*;
import com.pluralsight.ui.UserInterface;
import com.pluralsight.util.Money;
import com.pluralsight.util.ReceiptWriter;

import java.util.List;

public class App {
    private final UserInterface ui = new UserInterface();

    public void run() {
        while (true) {
            ui.clear();
            ui.header("Arc Deli & Co.");
            ui.sub("Crafted Subs • Toasted • Fresh");
            ui.line();
            ui.option("1) New Order");
            ui.option("0) Exit");
            int pick = ui.pick("Choose:");
            if (pick == 1) handleOrder();
            else if (pick == 0) { ui.ok("Goodbye!"); break; }
            else { ui.warn("Invalid choice"); ui.pause(); }
        }
    }

    private void handleOrder() {
        Order order = new Order();
        while (true) {
            ui.clear();
            ui.header("Order Station");
            ui.sub("Newest items show first");
            ui.line();
            ui.option("1) Add Sandwich");
            ui.option("2) Add Drink");
            ui.option("3) Add Chips");
            ui.option("4) Checkout");
            ui.option("0) Cancel Order");
            ui.line();
            printOrderList(order);
            int pick = ui.pick("Choose:");
            switch (pick) {
                case 1 -> addSandwich(order);
                case 2 -> addDrink(order);
                case 3 -> addChips(order);
                case 4 -> checkout(order);
                case 0 -> { ui.warn("Order canceled."); ui.pause(); return; }
                default -> { ui.warn("Invalid choice"); ui.pause(); }
            }
        }
    }

    private void addSandwich(Order order) {
        PricedItem s = SandwichBuilders.build(ui); // note: PricedItem
        order.addItem(s);
        ui.ok("Sandwich added.");
        ui.pause();
    }

    private void addDrink(Order order) {
        PricedItem d = ExtrasBuilders.buildDrink(ui); // note: PricedItem
        order.addItem(d);
        ui.ok("Drink added.");
        ui.pause();
    }

    private void addChips(Order order) {
        PricedItem c = ExtrasBuilders.buildChips(ui); // note: PricedItem
        order.addItem(c);
        ui.ok("Chips added.");
        ui.pause();
    }

    private void printOrderList(Order order) {
        List<PricedItem> items = order.itemsNewestFirst();
        if (items.isEmpty()) { ui.info("No items yet."); return; }
        int idx = items.size();
        for (PricedItem it : items) {
            System.out.printf("%2d) %s  —  $%s%n", idx--, it.title(), Money.format(it.price()));
        }
        System.out.printf("Total: $%s%n", Money.format(order.total()));
    }

    private void checkout(Order order) {
        if (order.isEmpty()) { ui.warn("Order is empty."); ui.pause(); return; }
        ui.clear();
        ui.header("Checkout");
        ui.line();
        for (PricedItem it : order.itemsNewestFirst()) {
            System.out.println(it.receiptLine());
            System.out.println();
        }
        System.out.println("TOTAL: $" + Money.format(order.total()));
        ui.line();
        String c = ui.ask("Confirm? (y/n):");
        if (c.equalsIgnoreCase("y")) {
            ReceiptWriter.saveReceipt(order);
            ui.ok("Receipt saved to resources/receipts.");
        } else {
            ui.warn("Checkout canceled.");
        }
        ui.pause();
    }
}
