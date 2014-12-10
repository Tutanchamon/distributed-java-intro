/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.supermarket.pointofsale;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.fomfel.supermarket.prototype.Product;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 *
 * @author Blazej
 */
public class ProductSaleService {
    private JmsTemplate jmsTemplate;
    private Destination reportingTopic;
    
    public void sellProduct(final Product product) {
        getJmsTemplate().send(reportingTopic, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                System.out.println("Selling item with ID = " + product.getId() + " for " + product.getPrice());
                MapMessage change = session.createMapMessage();
                change.setInt("id", product.getId());
                change.setDouble("price", product.getPrice());
                return change;
            }
        });

    }

    /**
     * @return the jmsTemplate
     */
    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    /**
     * @param jmsTemplate the jmsTemplate to set
     */
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * @return the productListTopic
     */
    public Destination getReportingTopic() {
        return reportingTopic;
    }

    /**
     * @param reportingTopic
     */
    public void setReportingTopic(Destination reportingTopic) {
        this.reportingTopic = reportingTopic;
    }
}
