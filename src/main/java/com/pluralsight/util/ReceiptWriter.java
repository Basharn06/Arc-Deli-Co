package com.pluralsight.util;

import com.pluralsight.models.Order;
import com.pluralsight.models.PricedItem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * Writes a human-readable receipt AND appends a CSV log of transactions.
 * Text file: src/main/resources/receipts/<orderNo>.txt
 * CSV file : src/main/resources/receipts/transactions.csv
 */
public final class ReceiptWriter {
    private ReceiptWriter() { }

    // save receipt + log
    public static String saveReceipt(Order order, String paymentMethod) {
        try {
            Path dir = Path.of("src", "main", "resources", "receipts");
            if (!Files.exists(dir)) Files.createDirectories(dir);

            String ts = now("yyyyMMdd-HHmmss");        // order number
            String humanTs = now("yyyy-MM-dd HH:mm:ss");

            // TEXT RECEIPT
            Path file = dir.resolve(ts + ".txt");
            try (BufferedWriter out = Files.newBufferedWriter(file)) {
                out.write(buildText(order, ts, humanTs, paymentMethod));
            }

            // CSV LOG (append)
            Path csv = dir.resolve("transactions.csv");
            boolean newFile = !Files.exists(csv);
            try (BufferedWriter out = Files.newBufferedWriter(csv, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                if (newFile) {
                    out.write("order_number,timestamp,total,payment_method,items\n");
                }
                String items = order.items().stream()
                        .map(PricedItem::title)
                        .collect(Collectors.joining(" | "));
                out.write(String.format("%s,%s,%s,%s,%s%n",
                        ts, humanTs, order.total(), paymentMethod, escapeCsv(items)));
            }

            return ts; // return order number
        } catch (IOException e) {
            System.out.println("[!] Failed to write receipt: " + e.getMessage());
            return null;
        }
    }

    private static String buildText(Order order, String orderNo, String humanTs, String pay) {
        StringBuilder sb = new StringBuilder();
        sb.append("Arc Deli & Co.\n");
        sb.append("Order #: ").append(orderNo).append("\n");
        sb.append("Date   : ").append(humanTs).append("\n");
        sb.append("Paid   : ").append(pay).append("\n");
        sb.append("=====================\n\n");
        for (PricedItem it : order.items()) {
            sb.append(it.receiptLine()).append("\n");
        }
        sb.append("\nTOTAL: $").append(order.total()).append("\n");
        sb.append("=====================\n");
        sb.append("Thank you!\n");
        return sb.toString();
    }

    private static String now(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    private static String escapeCsv(String s) {
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }
}
