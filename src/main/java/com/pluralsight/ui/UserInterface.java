package com.pluralsight.ui;

import java.util.Scanner;

// console ui
public class UserInterface {
    private final Scanner in = new Scanner(System.in); // input

    // clear screen
    public void clear() { System.out.print("\033[H\033[2J"); System.out.flush(); }

    // header banner
    public void header(String text) {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.printf ("║ %-41s ║%n", text);
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    // subtitle line
    public void sub(String t) { System.out.println("   " + t); }

    // rule line
    public void line() { System.out.println("────────────────────────────────────────────"); }

    // menu option
    public void option(String t) { System.out.println("• " + t); }

    // info msg (no [i])
    public void info(String m) { System.out.println(m); }

    // warn msg
    public void warn(String m) { System.out.println("[!] " + m); }

    // ok msg
    public void ok(String m) { System.out.println("[✓] " + m); }

    // ask text
    public String ask(String p) { System.out.print(p + " "); return in.nextLine().trim(); }

    // ask int
    public int pick(String p) {
        System.out.print(p + " ");
        try { return Integer.parseInt(in.nextLine().trim()); }
        catch (Exception e) { return -1; }
    }

    // bounded int
    public int pickInRange(String p, int min, int max) {
        while (true) {
            int v = pick(p);
            if (v >= min && v <= max) return v;
            warn("Pick " + min + "-" + max);
        }
    }

    // ask yes/no
    public boolean askYesNo(String p) {
        while (true) {
            String s = ask(p + " (y/n):").toLowerCase();
            if (s.equals("y")) return true;
            if (s.equals("n")) return false;
            warn("Type y or n");
        }
    }

    // pause wait
    public void pause() { System.out.print("\nPress ENTER to continue..."); in.nextLine(); }
}
