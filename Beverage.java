/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cafeorderingsystem;

/**
 *
 * @author anisp
 */
public class Beverage extends MenuItem implements Customizable{
   
     private String size;
    private String[] extras;
    private int extraCount;
    
    public Beverage(String name, double price) {
        super(name, price);  
        this.size = "Regular"; // Set default size instead of null assignment
        this.extras = new String[5];  
        this.extraCount = 0;
    }
    
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    @Override
    public String getDescription() {
        String description = name;
        
        if (extraCount > 0) {
            description += " with ";
            for (int i = 0; i < extraCount; i++) {
                description += extras[i];
                if (i < extraCount - 1) {
                    description += ", ";
                }
            }
        }
        
        return description;
    }
    
    @Override
    public void addExtra(String extra) {
        if (extraCount < 5) {
            extras[extraCount] = extra;
            extraCount++;
        }
    }
    
    @Override
    public double calculateExtraCharge() {
        return extraCount * 0.50;
    }
    
    public double getTotalPrice() {
        return price + calculateExtraCharge();
    }
}