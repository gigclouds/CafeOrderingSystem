/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cafeorderingsystem;

/**
 *
 * @author anisp
 */
public class Food extends MenuItem{
    private String category;
    
    public Food(String name, double price, String category) {
        super(name, price);
        this.category = category;
    }
    
    @Override
    public String getDescription() {
        return name + " (" + category + ")";
    }
    
    public String getCategory() {
        return category;
    }
}
