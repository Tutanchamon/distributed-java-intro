/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.supermarket.pointofsale;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Blazej
 */
public class PriceChangeService implements MessageListener {
    
    private PointOfSaleHolder posHolder;
    
    @Override
    public void onMessage(Message message) {
        if (message instanceof MapMessage) {
            MapMessage mapMessage = (MapMessage) message;
            try {
                 Double price = mapMessage.getDouble("price");
                 Integer id = mapMessage.getInt("id");
                 System.out.println("New price for product with ID = " + id + ": " + price);
                 posHolder.getPointOfSale().updatePrice(id, price);
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
            
        } else {
            throw new IllegalArgumentException("Wrong type of message");
        }
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
