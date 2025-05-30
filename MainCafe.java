/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cafeorderingsystem;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * @author anisp
 */
public class MainCafe {

    private static Menu menu;
    private static Scanner scanner;
    private static List<Order> orderHistory;
    
    public static void main(String[] args) {
        System.out.println("DEBUG: Program starting...");
        
        scanner = new Scanner(System.in);
        orderHistory = new ArrayList<>();
        initializeMenu();
        
        System.out.println("=== WELCOME TO CAFE ORDERING SYSTEM ===\n");
        
        boolean running = true;
        while (running) {
            try {
                displayMainMenu();
                int choice = getUserChoice();
                
                switch (choice) {
                    case 1:
                        displayMenu();
                        break;
                    case 2:
                        createOrder();
                        break;
                    case 3:
                        checkStock();
                        break;
                    case 4:
                        viewOrderHistory();
                        break;
                    case 5:
                        System.out.println("Thank you for using our system!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear buffer on error
            }
        }
        scanner.close();
    }
    
    private static void initializeMenu() {
        menu = new Menu();
        
        menu.addMenuItem(new Food("Chicken Rice", 5.0, "Rice"));
        menu.addMenuItem(new Food("Gepuk Rice", 6.0, "Rice"));
        menu.addMenuItem(new Food("Mee Soup", 6.0, "Mee"));
        menu.addMenuItem(new Food("Bihun Soup", 4.0, "Mee"));
        menu.addMenuItem(new Food("Sandwich", 3.50, "Snack"));
        
        menu.addMenuItem(new Beverage("Coffee", 3.0));
        menu.addMenuItem(new Beverage("Tea", 2.0));
        menu.addMenuItem(new Beverage("Chocolate", 2.0));
        menu.addMenuItem(new Beverage("Water", 1.50));
        menu.addMenuItem(new Beverage("Juice", 3.0));
        
        menu.setFoodStock(50);
        menu.setBeverageStock(100);
    }
    
    private static void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. View Menu");
        System.out.println("2. Place Order");  
        System.out.println("3. Check Stock");
        System.out.println("4. View Order History");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }
    
    private static int getUserChoice() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.print("Please enter a number (1-5): ");
                    continue;
                }
                int choice = Integer.parseInt(input);
                return choice;
            } catch (NumberFormatException e) {
                System.out.print("Error: Please enter a valid number (1-5): ");
            }
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n=== CAFE MENU ===");
        System.out.println(menu.displayMenu());
        System.out.println("Current Stock - Food: " + menu.getFoodStock() + 
                         ", Beverages: " + menu.getBeverageStock());
    }
    
    private static void createOrder() {
        try {
            Customer customer = getCustomerInfo();
            Order order = getOrderType(customer);
            
            addItemsToOrder(order);
            processPayment(order);
            
            saveReceiptToFile(order);
            menu.updateStock(order);
            orderHistory.add(order);
            
            System.out.println("\nOrder placed successfully!");
            order.printReceipt();
            
        } catch (Exception e) {
            System.out.println("Error creating order: " + e.getMessage());
        }
    }
    
    private static Customer getCustomerInfo() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        
        
        return new Customer(name);
    }
    
    private static Order getOrderType(Customer customer) throws IllegalArgumentException {
        System.out.println("\nSelect order type:");
        System.out.println("1. Dine In");
        System.out.println("2. Takeaway");
        System.out.print("Choose: ");
        
        int type = getUserChoice();
        
        switch (type) {
            case 1:
                System.out.print("Enter table number: ");
                int tableNumber = getUserChoice();
                return new DineInOrder(customer, tableNumber);
                
            case 2:
                System.out.println("Order will be ready for pickup in 15 minutes");
                return new Takeaway(customer);
                
            default:
                throw new IllegalArgumentException("Invalid order type selected.");
        }
    }
    private static void customizeBeverage(Beverage beverage) {
    System.out.print("Add extras? (y/n): ");
    String response = scanner.nextLine().trim();
    
    if (response.toLowerCase().startsWith("y")) {
        System.out.println("Available extras: Sugar, Honey, Ice, Cream");
        System.out.print("Enter extras (comma separated): ");
        String extrasInput = scanner.nextLine().trim();
        
        if (!extrasInput.isEmpty()) {
            String[] extras = extrasInput.split(",");
            for (String extra : extras) {
                beverage.addExtra(extra.trim());
            }
        }
    }
}
    private static void addItemsToOrder(Order order) throws InvalidMenuItemException, InsufficientStockException {
    while (true) {
        displayMenu();
        System.out.print("\nEnter item name (or 'done' to finish): ");
        String itemName = scanner.nextLine().trim();
        
        if (itemName.equalsIgnoreCase("done")) {
            if (order.getItems().isEmpty()) {
                throw new InvalidMenuItemException("Order must contain at least one item.");
            }
            break;
        }
        
        if (itemName.isEmpty()) {
            System.out.println("Please enter a valid item name.");
            continue;
        }
        
        try {
            MenuItem item = menu.getMenuItemByName(itemName);
            if (item == null) {
                System.out.println("Error: Item '" + itemName + "' not found in menu. Please try again.");
                continue;
            }
            
            if (!menu.checkAvailability(item)) {
                System.out.println("Error: Item '" + itemName + "' is out of stock. Please choose another item.");
                continue;
            }
            
            // IMPROVED: Better beverage handling
            if (item instanceof Beverage) {
                // Create a fresh copy for customization
                Beverage customBeverage = new Beverage(item.getName(), item.getPrice());
                customizeBeverage(customBeverage);
                order.addItem(customBeverage);
                System.out.println("Added: " + customBeverage.getDescription());
            } else {
                // For food items, we can use the original since they're not customized
                order.addItem(item);
                System.out.println("Added: " + item.getName());
            }
            
        } catch (Exception e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }
}
    
    private static void processPayment(Order order) throws PaymentProcessingException {
    double total = order.calculateTotal();
    
    System.out.println("\nOrder total: RM " + String.format("%.2f", total));
    
    System.out.println("Select payment method:");
    System.out.println("1. Cash");
    System.out.println("2. Credit Card");
    System.out.println("3. Mobile Wallet");
    System.out.print("Choose: ");
    
    int paymentChoice = getUserChoice();
    String paymentMethod;
    
    switch (paymentChoice) {
        case 1: 
            paymentMethod = "Cash"; 
            break;
        case 2: 
            paymentMethod = "Credit Card"; 
            break;
        case 3: 
            paymentMethod = "Mobile Wallet"; 
            break;
        default: 
            throw new PaymentProcessingException("Invalid payment method selected.");
    }
    
    // SIMPLIFIED: No delivery logic
    Payment payment = new Payment(paymentMethod, order.calculateTotal());
    
    System.out.print("Processing payment");
    for (int i = 0; i < 3; i++) {
        try {
            Thread.sleep(500);
            System.out.print(".");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    payment.setPaymentStatus(true);
    order.setPayment(payment);
    System.out.println("\nPayment successful!");
}    
    private static void saveReceiptToFile(Order order) {
        try {
            String fileName = "receipt_" + order.orderId + "_" + 
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
            
            FileWriter writer = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(writer);
            
            // Capture receipt output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(baos));
            
            order.printReceipt();
            
            System.setOut(originalOut);
            
            printWriter.println("=== CAFE RECEIPT ===");
            printWriter.println("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            printWriter.println();
            printWriter.print(baos.toString());
            
            if (order.payment != null) {
                printWriter.println("\n=== PAYMENT DETAILS ===");
                printWriter.println(order.payment.printReceipt());
            }
            
            printWriter.close();
            System.out.println("Receipt saved to: " + fileName);
            
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }
    
    private static void checkStock() {
        System.out.println("\n=== STOCK INFORMATION ===");
        System.out.println("Food Stock: " + menu.getFoodStock());
        System.out.println("Beverage Stock: " + menu.getBeverageStock());
        
        if (menu.getFoodStock() < 10) {
            System.out.println("WARNING: Food stock is running low!");
        }
        if (menu.getBeverageStock() < 20) {
            System.out.println("WARNING: Beverage stock is running low!");
        }
    }
    
    private static void viewOrderHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("\nNo orders found.");
            return;
        }
        
        System.out.println("\n=== ORDER HISTORY ===");
        for (int i = 0; i < orderHistory.size(); i++) {
            Order order = orderHistory.get(i);
            System.out.println((i + 1) + ". Order ID: " + order.orderId + 
                             " - Customer: " + order.customer.getName() + 
                             " - Total: RM " + String.format("%.2f", order.calculateTotal()));
        }
        
        System.out.print("\nEnter order number to view details (or 0 to go back): ");
        try {
            int choice = getUserChoice();
            if (choice > 0 && choice <= orderHistory.size()) {
                orderHistory.get(choice - 1).printReceipt();
            }
        } catch (Exception e) {
            System.out.println("Invalid selection.");
        }
    }
}