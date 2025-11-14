package com.pluralsight.util;

import com.pluralsight.models.Order;
import com.pluralsight.models.PricedItem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptWriter {

    public static String saveReceipt(Order order, String paymentMethod) {
        try {
            // 🔹 Save in project root folder: /receipts
            Path dir = Paths.get("receipts");
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            // Debug print so you know exactly where receipts are saved
            System.out.println("Saving receipts in: " + dir.toAbsolutePath());

            // 🔹 Counter file
            Path counterFile = dir.resolve("order_counter.txt");
            int nextOrderNumber = readAndIncrementCounter(counterFile);

            String orderLabel = "order#" + nextOrderNumber;
            LocalDateTime now = LocalDateTime.now();

            // ---------- Write receipt ----------
            Path receiptFile = dir.resolve(orderLabel + ".txt");
            try (BufferedWriter out = Files.newBufferedWriter(receiptFile)) {
                out.write(buildReceipt(order, orderLabel, paymentMethod, now));
            }

            // ---------- Log to CSV ----------
            Path csv = dir.resolve("transactions.csv");
            boolean newFile = !Files.exists(csv);

            try (BufferedWriter out = Files.newBufferedWriter(
                    csv,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND)) {

                if (newFile) {
                    out.write("order_number,timestamp,total,payment_method,items\n");
                }

                String items = order.items().stream()
                        .map(PricedItem::title)
                        .collect(Collectors.joining(" | "));

                String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                out.write(orderLabel + "," +
                        timestamp + "," +
                        order.total() + "," +
                        paymentMethod + "," +
                        escapeCsv(items) + "\n");
            }

            return orderLabel;

        } catch (IOException e) {
            System.out.println("Error writing receipt: " + e.getMessage());
            return null;
        }
    }

    private static int readAndIncrementCounter(Path counterFile) throws IOException {
        int current = 0;

        if (Files.exists(counterFile)) {
            List<String> lines = Files.readAllLines(counterFile);
            if (!lines.isEmpty()) {
                try {
                    current = Integer.parseInt(lines.get(0).trim());
                } catch (NumberFormatException ignored) {
                    current = 0;
                }
            }
        }

        int next = current + 1;

        Files.writeString(
                counterFile,
                String.valueOf(next),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );

        return next;
    }

    private static String buildReceipt(Order order, String orderLabel, String paymentMethod, LocalDateTime now) {
        StringBuilder sb = new StringBuilder();

        sb.append("========================================\n");
        sb.append("          ARC DELI & CO. RECEIPT\n");
        sb.append("========================================\n");

        sb.append("Order Number : ").append(orderLabel).append("\n");
        sb.append("Date         : ").append(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n");
        sb.append("Time         : ").append(now.format(DateTimeFormatter.ofPattern("hh:mm a"))).append("\n");
        sb.append("Payment      : ").append(paymentMethod.toUpperCase()).append("\n");

        sb.append("----------------------------------------\n");
        sb.append("ITEMS\n");
        sb.append("----------------------------------------\n");

        for (PricedItem item : order.items()) {
            sb.append(item.receiptLine()).append("\n");
        }

        sb.append("----------------------------------------\n");
        sb.append(String.format("TOTAL                         $%.2f\n", order.total()));
        sb.append("========================================\n");
        sb.append("       THANK YOU FOR YOUR ORDER!\n");
        sb.append("========================================\n");

        return sb.toString();
    }

    private static String escapeCsv(String s) {
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }
}
