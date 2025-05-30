/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cafeorderingsystem;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author anisp
 */
abstract class Order {
     protected static int nextOrderId = 1001;
    protected String orderId;
    protected Customer customer;
    protected List<MenuItem> items;
    protected Payment payment;
    
    public Order(Customer customer) {
        this.orderId = "ORD" + nextOrderId++;
        this.customer = customer;
        this.items = new ArrayList<>();
    }
    
    public void addItem(MenuItem item) {
        items.add(item);
    }
    
    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : items) {
            if (item instanceof Beverage) {
                total += ((Beverage) item).getTotalPrice();
            } else {
                total += item.getPrice();
            }
        }
        return total;
    }
    
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    
    public List<MenuItem> getItems() {
        return items;
    }
    
    public abstract void printReceipt();
}