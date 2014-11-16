/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.charityfleamarket.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Blazej
 */
public class Recipient extends Customer implements PropertyChangeListener {

    static final Logger log = LoggerFactory.getLogger(Recipient.class);
    static final Random random = new Random();
    private Auction auction = null;
    private List<Item> items;
    private boolean registeredInAuction;
    private final ReentrantLock lock;
    private final Condition waitCondition;

    public Recipient(String name, MarketManager marketManager, Chairman chairman) {
        super(name, marketManager, chairman);
        items = new ArrayList<>();
        this.lock = new ReentrantLock();
        waitCondition = lock.newCondition();

    }

    @Override
    public String toString() {
        return "Recipient " + customerName;
    }

    @Override
    public void run() {
        MAINLOOP:
        while (getMarketManager().getCharityFleaMarket().getStatus() != MarketStatus.FINISHED) {
            while (getMarketManager().getCharityFleaMarket().getStatus() == MarketStatus.REGISTRATION) {
                try {
//                    log.debug("Recipent {{}} is trying to register", this.customerName);
//                    log.debug("Recipent {{}} will wait for {}", this.customerName, this.getRegistrationTime());
                    sleep(getRegistrationTime() * 500);
                    if (getMarketManager().registerCustomer(this)) {
                        log.debug("Recipent {{}} registered to the Market", this.customerName);
                        break;
                    } else {
                        sleep(getRegistrationTime() * 500);
                    }
                } catch (InterruptedException ex) {
                    //log.error(customerName, ex);
                    break MAINLOOP;
                }
            }
            while (getMarketManager().getCharityFleaMarket().getStatus() == MarketStatus.REGISTRATION) {
                // just wait for end of the registration
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
//                    log.debug("InterruptedException while waiting", ex);
                    break MAINLOOP;
                }
            }
            try {
                // wait 5-10 seconds for any auctions
                sleep(7500);

            } catch (InterruptedException ex) {
//                log.error("InterruptedException while waiting", ex);
                break MAINLOOP;
            }
            while (getMarketManager().getCharityFleaMarket().getStatus() == MarketStatus.OPENED) {
                if (!registered) {
                    break MAINLOOP;
                }
                lock.lock();

                try {
                    auction = chairman.getCurrentAuction();
                    if (auction != null) {
                        // Recipient waits up to 5 seconds before registering for auction.
                        int waitTime = random.nextInt(6);
                        sleep(waitTime * 1000);
                        // randomize whether the Recipient wants to take part in actual auction
                        if (random.nextBoolean()) {
                            //log.debug("Trying to register for auction {}", auction);
                            boolean isAdded = chairman.registerRecipient(this);
                            if (isAdded) {
                                setRegisteredInAuction(true);
//                            log.debug("{} starts waiting", this);
                                try {
                                    while (isRegisteredInAuction()) {
                                        sleep(1000);
                                    }
                                } finally {
                                    lock.unlock();
//                                    log.debug("{} finished waiting after auction", this);
                                }
                            }
                        }
                    } else {
                        sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    //log.error("InterruptedException:", ex);
                    break MAINLOOP;
                }
                if (marketManager.getCharityFleaMarket().getStatus() == MarketStatus.FINISHED) {
//                    log.debug("Market finished - {} leaving with items {}", this, items);
                    break;
                }
            }
        }
        log.info("{} says good bye leaving with items {}", customerName, getItems());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Auction.ACTIVE:
                Boolean isActive = (Boolean) evt.getNewValue();
                if (!isActive) {
//                    log.debug("Setting registeredInAuction to false");
                    setRegisteredInAuction(false);
                }
                break;
            case Auction.WINNER:
                Recipient winner = (Recipient) evt.getNewValue();
                if (winner.equals(this)) {
                    try {
                        //getItems().add(auction.getItem());
                        //log.info("{} won {}", this.getCustomerName(), auction.getItem());
                        // If auction was won by recipient, recipient waits additional
                        // 5 to 15 seconds before registering for another auction.
                        int waitTime = new Random().nextInt(11) + 5;
//                        log.debug("Winner will wait for {} seconds", waitTime);
                        sleep((waitTime) * 1000);
                    } catch (InterruptedException ex) {
                        //log.error(customerName, ex);

                    }
                }
        }
    }

    /**
     * @return the items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        items.add(item);
        try {
                        //getItems().add(auction.getItem());
            //log.info("{} won {}", this.getCustomerName(), auction.getItem());
            // If auction was won by recipient, recipient waits additional
            // 5 to 15 seconds before registering for another auction.
            int waitTime = new Random().nextInt(11) + 5;
//                        log.debug("Winner will wait for {} seconds", waitTime);
            sleep((waitTime) * 1000);
        } catch (InterruptedException ex) {
            //log.error(customerName, ex);

        }
    }

    /**
     * @return the registeredInAuction
     */
    public boolean isRegisteredInAuction() {
        return registeredInAuction;
    }

    /**
     * @param registeredInAuction the registeredInAuction to set
     */
    public void setRegisteredInAuction(boolean registeredInAuction) {
        this.registeredInAuction = registeredInAuction;
    }
}
