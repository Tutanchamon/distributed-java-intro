/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.supermarket.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Blazej
 */
public class Reporting {
    public static final Map<Integer, Double> PRODUCTS = new HashMap<>();

    public void updateReport(Integer id, double price) {
        System.out.println("Incoming update: ID = " + id + ", PRICE = " + price);
        Double current = PRODUCTS.get(id);
        current = current != null ? current + price : price;
        PRODUCTS.put(id, current);
    }
    
    public void printReport() {
        for (Map.Entry<Integer, Double> productEntry : PRODUCTS.entrySet()) {
                    System.out.println(productEntry.getKey() + ": " + productEntry.getValue());
                }
    }
}
