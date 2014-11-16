/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.charityfleamarket.model;

/**
 *
 * @author Blazej
 */
public class Item {
    
    public Item(String name) {
        this.name = name;
    }
    
    private final String name;
    
    @Override
    public String toString() {
        return name;
    }
    
}
