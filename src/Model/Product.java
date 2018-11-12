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
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min, max;
    
    public Product(int productID, String name, double price, int inStock, int min, int max, ObservableList associatedParts) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
        this.associatedParts = associatedParts;
    }
    
   
    
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
    public void setInStock(int inStock) {
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
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    //WORK
    public boolean removeAssociatedPart(int partID) {
        return true;
    }
    //WORK
    public Part lookupAssociatedPart(int partID) {
        return (Part) associatedParts;
    }
    
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    
    //productID
    public void setProductID(int productID) {
        this.productID = productID;
    }
    
    public int getProductID() {
        return productID;
    }
}
