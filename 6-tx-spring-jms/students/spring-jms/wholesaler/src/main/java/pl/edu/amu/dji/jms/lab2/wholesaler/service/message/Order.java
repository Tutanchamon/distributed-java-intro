/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.amu.dji.jms.lab2.wholesaler.service.message;

/**
 *
 * @author Blazej
 */
public class Order {
    private int quantity;
    private String retailerID;

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the retailerID
     */
    public String getRetailerID() {
        return retailerID;
    }

    /**
     * @param retailerID the retailerID to set
     */
    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }
}
