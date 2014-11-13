package pl.edu.amu.dji.jms.lab2.wholesaler.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.amu.dji.jms.lab2.wholesaler.service.message.Order;

@Service("orderService")
public class OrderService {
    @Transactional
    public void order(Order order) {

        int quantity = order.getQuantity();
        String retailerID = order.getRetailerID();

        System.out.println("Ordered quantity: " + quantity + " by " + retailerID);

    }
}
