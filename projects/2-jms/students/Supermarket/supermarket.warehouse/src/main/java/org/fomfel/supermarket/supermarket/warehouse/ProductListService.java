/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.supermarket.supermarket.warehouse;

import java.io.Serializable;
import java.util.Map;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.fomfel.supermarket.prototype.Product;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 *
 * @author Blazej
 */
public class ProductListService {

    private JmsTemplate jmsTemplate;

    private Destination productListTopic;
    private Destination productPriceChangeTopic;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setProductListTopic(Destination productListTopic) {
        this.productListTopic = productListTopic;
    }

    public void setProductPriceChangeTopic(Destination productPriceChangeTopic) {
        this.productPriceChangeTopic = productPriceChangeTopic;
    }

    public void sendProductList(final Map<Integer, Product> productList) {
        getJmsTemplate().send(productListTopic, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objectMessage = session.createObjectMessage((Serializable) productList);

                return objectMessage;
            }

        });
    }

    public void changeProductPrice(final Product product) {
        getJmsTemplate().send(getProductPriceChangeTopic(), new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
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
     * @return the productListTopic
     */
    public Destination getProductListTopic() {
        return productListTopic;
    }

    /**
     * @return the productPriceChangeTopic
     */
    public Destination getProductPriceChangeTopic() {
        return productPriceChangeTopic;
    }
}
