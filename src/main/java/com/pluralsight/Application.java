package com.pluralsight;

import com.pluralsight.ui.UserInterface;

// app entry
public class Application {
    // main start
    public static void main(String[] args) {
        UserInterface ui = new UserInterface(); // ui init
        while (true) {
            ui.clear();                           // clear
            ui.header("Arc Deli & Co.");          // brand
            ui.sub("Crafted Subs • Toasted • Fresh"); // tagline
            ui.line();
            ui.option("1) New Order");
            ui.option("0) Exit");
            int pick = ui.pick("Choose:");
            if (pick == 1) {
                ui.info("Order flow coming soon…"); // temp
                ui.pause();                         // wait
            } else if (pick == 0) {
                ui.ok("Goodbye!");                  // bye
                break;
            } else {
                ui.warn("Invalid choice");          // bad input
                ui.pause();
            }
        }
    }
}
