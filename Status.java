/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cafeorderingsystem;
import java.time.LocalDateTime;
/**
 *
 * @author anisp
 */
public class Status {
    private LocalDateTime orderTime;
    private double distance;
    
    public Status(LocalDateTime orderTime, double distance) {
        this.orderTime = orderTime;
        this.distance = distance;
    }
    
    public String getEstimatedTime() {
        return " 15 minutes";
    }
    
    public LocalDateTime getOrderTime() {
        return orderTime;
    }
   
}


