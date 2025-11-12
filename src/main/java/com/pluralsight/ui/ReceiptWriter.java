package com.pluralsight.util;

import com.pluralsight.models.Order;
import com.pluralsight.models.PricedItem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

 // Writes a receipt file for a completed order.

public final class ReceiptWriter {
    private ReceiptWriter() { } // utility only

    // save receipt
    public static void saveReceipt(Order order) {
        try {
            Path dir = Path.of("src", "main", "resources", "receipts");  // receipts dir
            if (!Files.exists(dir)) Files.createDirectories(dir);        // ensure folder

            String ts = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")); // timestamp
            Path file = dir.resolve(ts + ".txt");                        // file name

            try (BufferedWriter out = Files.newBufferedWriter(file)) {   // buffered write
                out.write(build(order));                                 // write content
            }
        } catch (IOException e) {
            System.out.println("[!] Failed to write receipt: " + e.getMessage()); // error msg
        }
    }

    // build receipt text
    private static String build(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Arc Deli & Co.\n");                   // shop name
        sb.append("Receipt\n");
        sb.append("=====================\n\n");
        for (PricedItem it : order.items()) {            // each line item
            sb.append(it.receiptLine()).append("\n");
        }
        sb.append("\nTOTAL: $").append(order.total()).append("\n"); // total
        return sb.toString();
    }
}
