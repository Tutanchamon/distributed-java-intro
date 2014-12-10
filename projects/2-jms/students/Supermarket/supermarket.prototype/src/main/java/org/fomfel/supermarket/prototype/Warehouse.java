/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.supermarket.prototype;

import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Blazej
 */
public class Warehouse {

    private static volatile Warehouse instance = null;

    public static Warehouse getInstance() {
        if (instance == null) {

            if (instance == null) {
                instance = new Warehouse();
            }
        }
        return instance;
    }

    // only private constructor
    private Warehouse() {
        products = new HashMap<>();
        pointsOfSale = new ArrayList<>();
        cloner = new Cloner();

        products.put(21, new Product(21, "Pulp Fiction Wallet", 40.79));
        products.put(22, new Product(22, "New Pink Floyd CD", 22.53));
        products.put(23, new Product(23, "Golden Earring", 48.22));

    }

    private Map<Integer, Product> products;
    private List<PointOfSale> pointsOfSale;
    private final Cloner cloner;

    public void registerPointOfSale(PointOfSale pointOfSale) {
        pointsOfSale.add(pointOfSale);
    }

    public void sendProducts() {
        for (PointOfSale pointOfSale : pointsOfSale) {
            pointOfSale.initProducts(cloner.deepClone(products));
        }
    }

    public void unregister(PointOfSale pointOfSale) {
        pointsOfSale.remove(pointOfSale);
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public void changePrice(Integer productId, Double price) {
        Product product = products.get(productId);
        if (product != null) {
            product.setPrice(price);
            products.put(productId, product);

            for (PointOfSale pointOfSale : pointsOfSale) {
                pointOfSale.updatePrice(productId, price);
            }
        } else {
            System.out.println("There is no product with ID = " + productId);
        }
    }
}
