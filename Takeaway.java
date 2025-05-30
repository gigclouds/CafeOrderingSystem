/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cafeorderingsystem;

/**
 *
 * @author anisp
 */
public class Takeaway extends Order{
    private String orderNumber;
    private static int nextOrderNumber = 101;
    
    public Takeaway(Customer customer) { // Changed parameter type
        super(customer);
        this.orderNumber = "T" + nextOrderNumber++;
    }
    
    public String getOrderNumber() {
        return orderNumber;
    }
    
    @Override
    public void printReceipt() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Type: Takeaway Order");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Order Number: " + orderNumber);
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