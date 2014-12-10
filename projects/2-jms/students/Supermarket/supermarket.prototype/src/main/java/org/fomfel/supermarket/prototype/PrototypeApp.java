/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.supermarket.prototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Blazej
 */
public class PrototypeApp {

    public static void main(String[] args) {
        Reporting reporting = new Reporting();
        Warehouse warehouse = Warehouse.getInstance();

        PointOfSale pos1 = new PointOfSale(reporting);
        PointOfSale pos2 = new PointOfSale(reporting);
        PointOfSale pos3 = new PointOfSale(reporting);
        PointOfSale pos4 = new PointOfSale(reporting);

        warehouse.registerPointOfSale(pos1);
        warehouse.registerPointOfSale(pos2);
        warehouse.registerPointOfSale(pos3);
        warehouse.registerPointOfSale(pos4);
        
        warehouse.sendProducts();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Available products:");
        for (Entry<Integer, Product> product : warehouse.getProducts().entrySet()) {
            System.out.println(product.getKey() + ":" + product.getValue());
        }

        //Sale part
        String productToSaleId;
        int productId;
        try {
            System.out.println("Select product to sale on pos 1:");
            productToSaleId = bufferedReader.readLine();
            productId = Integer.parseInt(productToSaleId);
            pos1.sale(productId);

            System.out.println("Select product to sale on pos 2:");
            productToSaleId = bufferedReader.readLine();
            productId = Integer.parseInt(productToSaleId);
            pos2.sale(productId);

            //Change price
            System.out.print("Select product ID which you'd like to change:");
            String name = bufferedReader.readLine();
            int idToChange = Integer.parseInt(name);
            System.out.print("New product price:");
            String price = bufferedReader.readLine();
            double newPrice = Double.parseDouble(price);

            warehouse.changePrice(idToChange, newPrice);

            //Sale part
            System.out.println("Select product to sale on pos 3:");
            productToSaleId = bufferedReader.readLine();
            productId = Integer.parseInt(productToSaleId);
            pos3.sale(productId);

            System.out.println("Select product to sale on pos 4:");
            productToSaleId = bufferedReader.readLine();
            productId = Integer.parseInt(productToSaleId);
            pos4.sale(productId);

            //Sales report
            System.out.println("Sales report:");
            for (Map.Entry entry : reporting.PRODUCTS.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (NumberFormatException ex) {
            System.out.println("You have not typed a valid int!");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
