/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.charityfleamarket.model;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Blazej
 */
public class MarketManager extends Thread {
    
    static final Logger log = LoggerFactory.getLogger(MarketManager.class);
    private CharityFleaMarket fleaMarket;
    private String managerName;
    private int timeForRegistration = 3;
    private final Random random = new Random();
    public final int MAX_DONORS;
    public final int MAX_RECIPENTS;
    public final int MAX_AUCTIONS;
    
    
    public MarketManager(String name) {
        this.managerName = name;
        MAX_DONORS = random.nextInt(10)+1;
        log.debug("Market Manager {} allows max {} donors", managerName, MAX_DONORS);
        MAX_RECIPENTS = random.nextInt(15)+1;
        log.debug("Market Manager {} allows max {} recipents", managerName, MAX_RECIPENTS);
        MAX_AUCTIONS = random.nextInt(50)+1;
    }
    public MarketManager(String managerName, CharityFleaMarket fleaMarket) {
        this(managerName);
        this.fleaMarket = fleaMarket;
        
    }
    
    public String getManagerName() {
        return managerName;
    }
    
    @Override
    public String toString() {
        return "MarketManager " + managerName; 
    }
    
    public CharityFleaMarket getCharityFleaMarket() {
        return fleaMarket;
    }
    
    public boolean registerCustomer(Customer customer) {
        if (fleaMarket.getStatus() == MarketStatus.REGISTRATION) {
            
            boolean registered;
            if (customer instanceof Donor) {
                registered = fleaMarket.getDonors().offer((Donor) customer);
            } else {
                registered = fleaMarket.getRecipents().offer((Recipient) customer);
            }
            customer.setRegistered(registered);
            if (registered) {
                log.info("Registering new {}", customer);
            }
            return registered;
        } else {
//            log.debug("Not registering Donor {} - it's too late", customer.getCustomerName());
            return false;
        }
    }
    
    @Override
    public void run() {
        log.info("Market Manager {} starts the registration for the Flea Market", managerName);
        fleaMarket.setStatus(MarketStatus.REGISTRATION);
//        log.debug("FLEA MARKET STATUS: {}", fleaMarket.getStatus());
        log.info("The Market Manager {} will close registration after {} seconds",
                this.managerName, this.timeForRegistration);
        try {
            fleaMarket.registerCustomers(50);
            sleep(timeForRegistration * 1000);
            log.info("Market Manager {} is going to close the registration", managerName);
//            log.debug("Setting MarketStatus to {}", MarketStatus.OPENED);
            fleaMarket.setStatus(MarketStatus.OPENED);
            // wake the Chairman up
            fleaMarket.getChairman().start();
            log.info("Number of registered donors: {}", fleaMarket.getDonors().size());
            log.info("Number of registered recipents: {}", fleaMarket.getRecipents().size());
            log.info("Number of items brought by donors: {}", fleaMarket.countTotalItems());
        } catch (InterruptedException ex) {
            log.error("Interrupted Exception:", ex);
        }
        
    }
    
    public void shutdownMarket() {
//        log.debug("MarketManager is shutting down the market");
        fleaMarket.setStatus(MarketStatus.FINISHED);
        fleaMarket.getCustomerService().shutdownNow();
        try {
            log.debug("SHUT DOWN!!!");
            if (fleaMarket.getCustomerService().awaitTermination(100, TimeUnit.SECONDS)) {
                System.out.println("Still waiting...");
                System.exit(0);
            }   
        } catch (InterruptedException ex) {
            log.error("Interrupted exception after trying to shutdown market", ex);
        }
        
    }
}
