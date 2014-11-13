/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.amu.dji.jms.lab2.wholesaler.service.message;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Blazej
 */
@Component(value = "orderConverter")
public class OrderConverter implements MessageConverter {

    public Message toMessage(Object o, Session sn) throws JMSException, MessageConversionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object fromMessage(Message msg) throws JMSException, MessageConversionException {
        MapMessage mapMessage = (MapMessage) msg;
        Order order = new Order();
        order.setQuantity(mapMessage.getInt("quantity"));
        order.setRetailerID(mapMessage.getString("retailerID"));
        return order;
    }
    
}
