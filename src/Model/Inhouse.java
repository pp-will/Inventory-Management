/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author wpittman
 */
public class Inhouse extends Part{
    static int machineID;

    public Inhouse(int partID, String name, double price, int inStock, int min, int max, int machineID) {
        super(partID, name, price, inStock, min, max);
        this.machineID = machineID;
    }
    
    
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
    
    public static int getMachineID() {
        return machineID;
    }
}

