/*
 * CS-360 Mobile Architecture & Programming - 23EW2
 * Project Three
 * Instructor: Jerome DiMarzio
 * Author: Greg Isajewicz
 */

package com.zybooks.inventoryapp;

public class InventoryItem {
    private String itemName;
    private int quantity;

    //CONSTRUCTOR
    public InventoryItem(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }
    //Accessors
    public String getItemName() {return itemName;}
    public int getQuantity() {return quantity;}

    //Modifiers
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
