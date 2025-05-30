/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cafeorderingsystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anisp
 */
public class ReceiptManager {
    private static final String RECEIPT_DIRECTORY = "receipts/";
    
    static {
        File directory = new File(RECEIPT_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    public static void saveReceipt(Order order, String content) throws IOException {
        String fileName = RECEIPT_DIRECTORY + "receipt_" + order.orderId + "_" + 
                         LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("=== CAFE RECEIPT ===");
            writer.println("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println();
            writer.println(content);
        }
    }
    
    public static List<String> getReceiptHistory() {
        File directory = new File(RECEIPT_DIRECTORY);
        List<String> receipts = new ArrayList<>();
        
        if (directory.exists()) {
            File[] files = directory.listFiles((dir, name) -> name.startsWith("receipt_") && name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    receipts.add(file.getName());
                }
            }
        }
        
        return receipts;
    }
}

