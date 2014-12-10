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
public class PointOfSale {

    private Map<Integer, Product> products;
    private Reporting reporting;

    public PointOfSale(Reporting reporting) {
        this.reporting = reporting;
    }
    
    public PointOfSale() {
        products = new HashMap<>();
    } 

    public void initProducts(Map<Integer, Product> products) {
        this.setProducts(products);
    }

    public void updatePrice(Integer id, double price) {
        if (!products.isEmpty()) {
            Product product = products.get(id);
            product.setPrice(price);
            getProducts().put(id, product);
        }

    }

    public void sale(Integer id) {
        if (!products.isEmpty()) {
            Product product = getProducts().get(id);
            reporting.updateReport(id, product.getPrice());
        }

    }

    /**
     * @return the products
     */
    public Map<Integer, Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(Map<Integer, Product> products) {
        this.products = products;
    }
}
