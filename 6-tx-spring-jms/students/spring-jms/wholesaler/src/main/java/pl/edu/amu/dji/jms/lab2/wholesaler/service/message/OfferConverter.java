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
@Component(value = "offerConverter")
public class OfferConverter implements MessageConverter {

    public Message toMessage(Object o, Session sn) throws JMSException, MessageConversionException {
        MapMessage mapMessage = sn.createMapMessage();
        Offer offer = (Offer) o;
        mapMessage.setDouble("price", offer.getPrice());
        mapMessage.setJMSReplyTo(offer.getReplyTo());
        return mapMessage;
    }

    public Object fromMessage(Message msg) throws JMSException, MessageConversionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
