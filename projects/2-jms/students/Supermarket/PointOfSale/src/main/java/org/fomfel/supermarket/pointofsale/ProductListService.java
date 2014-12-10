/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.supermarket.pointofsale;

import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.fomfel.supermarket.prototype.Product;

/**
 *
 * @author Blazej
 */
public class ProductListService implements MessageListener {

    private Map<Integer, Product> products;
    private PointOfSaleHolder posHolder;

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                ObjectMessage objectMessage = (ObjectMessage) message;
                Map<Integer, Product> productMap = (Map<Integer, Product>) objectMessage.getObject();
                getPosHolder().getPointOfSale().setProducts(productMap);
                products = productMap;
                System.out.println("New Product List:");
                for (Map.Entry<Integer, Product> productEntry
                        : posHolder.getPointOfSale().getProducts().entrySet()) {
                    System.out.println(productEntry.getKey() + ": " + productEntry.getValue());
                }
            } catch (JMSException ex) {
                System.out.println("JMSException!");
                ex.printStackTrace();
            }
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

    /**
     * @return the posHolder
     */
    public PointOfSaleHolder getPosHolder() {
        return posHolder;
    }

    /**
     * @param posHolder the posHolder to set
     */
    public void setPosHolder(PointOfSaleHolder posHolder) {
        this.posHolder = posHolder;
    }

}
