/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author wpittman
 */
public class Product {
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min, max;
    
    
    
    //name
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    //price
    public void setPrice(double price) {
        this.price = price;
    }
    
    public double getPrice() {
        return price;
    }
    //inStock
    public void inStock(int inStock) {
        this.inStock = inStock;
    }
    
    public int getInStock() {
        return inStock;
    }
    //min
    public void setMin(int min) {
        this.min = min;
    } 
    
    public int getMin() {
        return min;
    }
    //max
    public void setMax(int max) {
        this.max = max;
    }
    
    public int getMax() {
        return max;
    }
    //addAssociatedPart ***NEEDS WORK
    public void addAssociatedPart(int partID) {
        
    }
    //WORK
    public boolean removeAssociatedPart(int partID) {
        return true;
    }
    //WORK
    public Part lookupAssociatedPart(int partID) {
        return (Part) associatedParts;
    }
    
    //productID
    public void setProductID(int productID) {
        this.productID = productID;
    }
    
    public int getProductID() {
        return productID;
    }
}
