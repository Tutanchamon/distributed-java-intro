package pl.edu.amu.dji.jms.lab2.wholesaler.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class OrderService implements MessageListener {

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            System.out.println("RetailerID: " + mapMessage.getString("retailerID"));
            System.out.println("Price:" + mapMessage.getDouble("quantity"));
        } catch (JMSException ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
}
