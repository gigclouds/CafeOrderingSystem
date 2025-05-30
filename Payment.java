/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cafeorderingsystem;

/**
 *
 * @author anisp
 */
class Payment {
    private boolean paymentStatus;
    private String paymentMethod;
    private double amount;
    
    public Payment(String paymentMethod, double amount) {
        this.paymentStatus = false;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }
    
    public double getTotalAmount() {
        return amount;
    }
    
    public String printReceipt() {
        String receipt = "";
        receipt += "Payment Method: " + paymentMethod + "\n";
        receipt += "Amount: RM" + String.format("%.2f", amount) + "\n";
        receipt += "Total Payment: RM " + String.format("%.2f", getTotalAmount()) + "\n";
        receipt += "Payment Status: " + (paymentStatus ? "Completed" : "Pending");
        
        return receipt;
    }
    
    public void setPaymentStatus(boolean status) {
        this.paymentStatus = status;
    }
    
    public boolean getPaymentStatus() {
        return paymentStatus;
    }
}