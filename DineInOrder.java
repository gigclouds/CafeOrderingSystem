/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cafeorderingsystem;

/**
 *
 * @author anisp
 */
public class DineInOrder extends Order {
     private int tableNumber;
    
    public DineInOrder(Customer customer, int tableNumber) {
        super(customer);
        this.tableNumber = tableNumber;
    }
    
    public int getTableNumber() {
        return tableNumber;
    }
    
    @Override
    public void printReceipt() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Type: Dine-In Order");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Table Number: " + tableNumber);
        System.out.println("Items:");
        
        for (MenuItem item : items) {
            if (item instanceof Beverage) {
                System.out.println("- " + item.getDescription() + 
                                  " : RM " + String.format("%.2f", ((Beverage) item).getTotalPrice()));
            } else {
                System.out.println("- " + item.getDescription() + 
                                  " : RM " + String.format("%.2f", item.getPrice()));
            }
        }
        
        System.out.println("Total: RM " + String.format("%.2f", calculateTotal()));
    }
}