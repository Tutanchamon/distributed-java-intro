/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.supermarket.pointofsale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import org.fomfel.supermarket.prototype.Product;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Blazej
 */
public class PointOfSaleApp {

    public static final String EXIT = "EXIT";

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        PointOfSaleHolder posHolder = (PointOfSaleHolder) context.getBean("pointOfSaleHolder");
        ProductSaleService saleService = (ProductSaleService) context.getBean("productSaleService");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String in = "";
        while (!in.equalsIgnoreCase(EXIT)) {
            System.out.println("1 - select available products, 2 - sell item");
            try {
                in = bufferedReader.readLine();
                if ("1".equals(in)) {
                    printProducts(posHolder);
                } else if ("2".equals(in)) {
                    System.out.println("Type ID of product to sell");
                    printProducts(posHolder);
                    if (!posHolder.getPointOfSale().getProducts().isEmpty()) {
                        in = bufferedReader.readLine();
                        int chosenId = Integer.parseInt(in);
                        Product productToSell = posHolder.getPointOfSale().getProducts().get(chosenId);
                        if (productToSell != null) {
                            saleService.sellProduct(productToSell);
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("NumberFormatException!");
                ex.printStackTrace();
            } catch (IOException ex) {
                System.out.println("IOException!");
                ex.printStackTrace();
            }

        }
    }

    private static void printProducts(PointOfSaleHolder posHolder) {
        if (posHolder.getPointOfSale().getProducts() == null || posHolder.getPointOfSale().getProducts().isEmpty()) {
            System.out.println("The list of products is not available or empty at the moment");
            return;
        }
        for (Map.Entry<Integer, Product> productEntry
                : posHolder.getPointOfSale().getProducts().entrySet()) {
            System.out.println(productEntry.getKey() + ": " + productEntry.getValue());
        }
    }

}
