package pl.edu.amu.dji.jms.lab2.retailer.service;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import org.springframework.jms.core.MessageCreator;

public class BuyService implements MessageListener {

    private JmsTemplate jmsTemplate;

    private Double maxPrice;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public void onMessage(Message message) {
        MapMessage messageCast = (MapMessage) message;
        try {
            double price = messageCast.getDouble("price");
            if (priceIsOk(price)) {
                MessageCreator messageCreator = new MessageCreator() {

            public Message createMessage(Session sn) throws JMSException {
               MapMessage msg = sn.createMapMessage();
               msg.setString("retailerID", getClass().getName());
               msg.setDouble(("quantity"), new Random().nextDouble());
               //msg.setJMSReplyTo(orderQueue);
               return msg;
            }
        };
                jmsTemplate.send(message.getJMSReplyTo(), messageCreator);
            }
        } catch (JMSException ex) {
            Logger.getLogger(BuyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private boolean priceIsOk(double price) {
        return maxPrice.compareTo(price)==1;
    }
}
