/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.supermarket.reporting;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.fomfel.supermarket.prototype.Reporting;

/**
 *
 * @author Blazej
 */
public class ReportingService implements MessageListener {

    private Reporting reporting = new Reporting();

    @Override
    public void onMessage(Message message) {
        if (message instanceof MapMessage) {
            MapMessage mapMessage = (MapMessage) message;
            try {
                System.out.println("Adding a sale fact report... ");
                Integer id = mapMessage.getInt("id");
                Double price = mapMessage.getDouble("price");
                reporting.updateReport(id, price);
                reporting.printReport();
            } catch (JMSException ex) {
                System.out.println("Exception during updating a report");
                ex.printStackTrace();
            }
        }
    }

}
