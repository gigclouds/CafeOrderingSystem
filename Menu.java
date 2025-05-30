/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cafeorderingsystem;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author anisp
 */
class Menu {
     private List<MenuItem> menuItems;
    private int foodStock;
    private int beverageStock;
    
    public Menu() {
        this.menuItems = new ArrayList<>();
        this.foodStock = 0;
        this.beverageStock = 0;
    }
    
    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }
    
    public MenuItem getMenuItemByName(String name) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    
    public boolean checkAvailability(MenuItem item) {
        if (item instanceof Food) {
            return foodStock > 0;
        } else if (item instanceof Beverage) {
            return beverageStock > 0;
        }
        return false;
    }
    
    public String displayMenu() {
        String menuDisplay = "";
        
        menuDisplay += "FOOD ITEMS:\n";
        for (MenuItem item : menuItems) {
            if (item instanceof Food) {
                String availability = checkAvailability(item) ? "" : " (Out of Stock)";
                menuDisplay += "- " + item.getDescription() +
                             " : RM " + String.format("%.2f", item.getPrice()) + availability + "\n";
            }
        }
        
        menuDisplay += "\nBEVERAGE ITEMS:\n";
        for (MenuItem item : menuItems) {
            if (item instanceof Beverage) {
                String availability = checkAvailability(item) ? "" : " (Out of Stock)";
                menuDisplay += "- " + item.getDescription() +
                             " : RM " + String.format("%.2f", item.getPrice()) + availability + "\n";
            }
        }
        
        return menuDisplay;
    }
    
    public void setFoodStock(int quantity) {
        this.foodStock = quantity;
    }
    
    public void setBeverageStock(int quantity) {
        this.beverageStock = quantity;
    }
    
    public void updateStock(Order order) throws InsufficientStockException {
        int foodCount = 0;
        int beverageCount = 0;
        
        for (MenuItem item : order.getItems()) {
            if (item instanceof Food) {
                foodCount++;
            } else if (item instanceof Beverage) {
                beverageCount++;
            }
        }
        
        if (foodStock < foodCount) {
            throw new InsufficientStockException("Insufficient food stock! Available: " + foodStock + ", Required: " + foodCount);
        }
        
        if (beverageStock < beverageCount) {
            throw new InsufficientStockException("Insufficient beverage stock! Available: " + beverageStock + ", Required: " + beverageCount);
        }
        
        foodStock -= foodCount;
        beverageStock -= beverageCount;
    }
    
    public int getFoodStock() {
        return foodStock;
    }
    
    public int getBeverageStock() {
        return beverageStock;
    }
}