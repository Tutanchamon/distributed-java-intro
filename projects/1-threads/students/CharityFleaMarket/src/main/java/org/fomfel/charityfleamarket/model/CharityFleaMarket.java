/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.charityfleamarket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.fomfel.charityfleamarket.model.util.RandomNameGenerator;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Blazej
 */
public class CharityFleaMarket {
    private static final Logger log = LoggerFactory.getLogger(CharityFleaMarket.class);
    private MarketStatus status = MarketStatus.BEFORE_OPEN;
    
    
    private volatile Chairman chairman;
    private MarketManager marketManager;
    private final BlockingQueue<Donor> donors;
    private final BlockingQueue<Recipient> recipents;
    private BlockingQueue<Auction> auctions;
    private ExecutorService customerService;
    
    public CharityFleaMarket() {
        chairman = new Chairman(this);
        marketManager = new MarketManager(RandomNameGenerator.generateRandomName(), this);
        donors = new ArrayBlockingQueue<>(marketManager.MAX_DONORS);
        recipents = new ArrayBlockingQueue<>(marketManager.MAX_RECIPENTS);
        auctions = new ArrayBlockingQueue<>(Chairman.AUCTIONS_QUEUE_SIZE);
        customerService = Executors.newFixedThreadPool(25);
        log.debug("Today's Market Manager is run by {{}}", marketManager.getManagerName());
        
    }

    /**
     * @return the status
     */
    public MarketStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(MarketStatus status) {
        this.status = status;
    }

    /**
     * @return the chairman
     */
    public Chairman getChairman() {
        return chairman;
    }

    /**
     * @param chairman the chairman to set
     */
    public void setChairman(Chairman chairman) {
        this.chairman = chairman;
    }

    /**
     * @return the marketManager
     */
    public MarketManager getMarketManager() {
        return marketManager;
    }

    /**
     * @param marketManager the marketManager to set
     */
    public void setMarketManager(MarketManager marketManager) {
        this.marketManager = marketManager;
    }
    
    private List<Customer> generateCustomers(int numberOfCustomers) {
        List<Customer> generatedCustomers = new ArrayList<>(numberOfCustomers);
        for (int i=0; i<numberOfCustomers; i++) {
            generatedCustomers.add(CustomerFactory.
                    generateCustomer(marketManager, chairman, new Random().nextBoolean())); 
        }
        return generatedCustomers;
    }
    
    public void registerCustomers(int numberOfCustomers) throws InterruptedException {
        List<Customer> generatedCustomers = generateCustomers(numberOfCustomers);
        setCustomerService(Executors.newFixedThreadPool(numberOfCustomers));
        for (Customer customer : generatedCustomers) {
            getCustomerService().submit(customer);
        }
        getCustomerService().shutdown();
        getCustomerService().awaitTermination(25, TimeUnit.SECONDS);
    }
    
    /**
     * @return the donors
     */
    public BlockingQueue<Donor> getDonors() {
        return donors;
    }

    /**
     * @return the recipents
     */
    public BlockingQueue<Recipient> getRecipents() {
        return recipents;
    }

    /**
     * @return the auctions
     */
    public BlockingQueue<Auction> getAuctions() {
        return auctions;
    }
    /**
     * Counts the number of items which were initially brought to the market
     * by donors.
     * @return number of items brought by donors
     */
    public int countTotalItems() {
        int total = 0;
        for (Donor donor : getDonors()) {
            total += donor.getItems().size();
        }
        return total;
    }

    /**
     * @return the customerService
     */
    public ExecutorService getCustomerService() {
        return customerService;
    }

    /**
     * @param customerService the customerService to set
     */
    public void setCustomerService(ExecutorService customerService) {
        this.customerService = customerService;
    }
    
    
}
