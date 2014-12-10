/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.supermarket.supermarket.warehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import org.fomfel.supermarket.prototype.Product;
import org.fomfel.supermarket.prototype.Warehouse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Blazej
 */
public class WarehouseApp {

    public static final String EXIT = "exit";

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        Warehouse warehouse = Warehouse.getInstance();
        ProductListService productListService = (ProductListService) context.getBean("productListService");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String in = "";
        while (!in.equalsIgnoreCase(EXIT)) {
            try {
                System.out.println("Products in the warehouse:");
                for (Entry<Integer, Product> productEntry : warehouse.getProducts().entrySet()) {
                    System.out.println(productEntry.getKey() + ": " + productEntry.getValue());
                }
                System.out.println("1 - send Full Product List, 2 - change product's price");
                in = bufferedReader.readLine();
                if ("1".equals(in)) {
                    productListService.sendProductList(warehouse.getProducts());
                } else if ("2".equals(in)) {
                    System.out.println("Type the product ID to change its price: ");
                    in = bufferedReader.readLine();
                    int chosenId = Integer.parseInt(in);
                    Product chosen = warehouse.getProducts().get(chosenId);
                    if (chosen != null) {
                        System.out.println(chosen);
                        System.out.print("Type the new price of a product: ");
                        in = bufferedReader.readLine();
                        Double newPrice = Double.parseDouble(in);
                        chosen.setPrice(newPrice);
                        productListService.changeProductPrice(chosen);
                    } else {
                        System.out.println("Product with given ID not found");
                    }
                }
            } catch (IOException ex) {
                System.err.println("Wrong input given");
            }

        }

    }
}
