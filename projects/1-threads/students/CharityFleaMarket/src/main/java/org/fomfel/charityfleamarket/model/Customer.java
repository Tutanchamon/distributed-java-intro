/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.charityfleamarket.model;

import java.util.Random;

/**
 *
 * @author Blazej
 */
public abstract class Customer extends Thread {
    protected boolean registered;
    protected String customerName;
    
    protected MarketManager marketManager;
    protected Chairman chairman;
    protected int registrationTime;
    
    public Customer(String customerName, MarketManager marketManager, Chairman chairman) {
        setName("Donor " + customerName);
        this.customerName = customerName;
        this.marketManager = marketManager;
        this.chairman = chairman;
        Random random = new Random();
        registrationTime = random.nextInt(10) + 1;

    }

    /**
     * @return the registered
     */
    public boolean isRegistered() {
        return registered;
    }

    /**
     * @param registered the registered to set
     */
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    /**
     * @return the registrationTime
     */
    public int getRegistrationTime() {
        return registrationTime;
    }

    /**
     * @param registrationTime the registrationTime to set
     */
    public void setRegistrationTime(int registrationTime) {
        this.registrationTime = registrationTime;
    }
    
    @Override
    public abstract void run();
}
