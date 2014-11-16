/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.charityfleamarket.model;

import java.util.Random;
import java.util.Stack;
import org.slf4j.Logger;
import org.fomfel.charityfleamarket.model.util.RandomItemGenerator;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Blazej
 */
public class Donor extends Customer {

    static final Logger log = LoggerFactory.getLogger(Donor.class);
    private Stack<Item> items;

    public Donor(String name, MarketManager marketManager, Chairman chairman, int numberOfItems) {
        super(name, marketManager, chairman);
        items = new Stack<>();
        Random random = new Random();
        for (int i = 0; i <= random.nextInt(numberOfItems); i++) {
            Item item = new Item(RandomItemGenerator.generateRandomName());
            items.push(item);
        }

    }

    @Override
    public String toString() {
        return "Donor " + customerName;
    }

    @Override
    public void run() {
        MAINLOOP: while (getMarketManager().getCharityFleaMarket().getStatus() != MarketStatus.FINISHED) {
            while (marketManager.getCharityFleaMarket().getStatus() == MarketStatus.REGISTRATION) {
                if (!registered) {
                    try {
                // log.debug("Donor {{}} is trying to register", this.customerName);
                        // log.debug("Donor {{}} is will wait for {}", this.customerName, this.registrationTime);
                        sleep(registrationTime * 500);
                        if (marketManager.registerCustomer(this)) {
                            //log.debug("Donor {{}} registered to the Market", this.customerName);
                            registered = true;

                        } else {
                            sleep(registrationTime * 500);
                        }
                        break;
                    } catch (InterruptedException ex) {
                        log.error(customerName, ex);
                        break MAINLOOP;
                    }
                }
            }
            while (marketManager.getCharityFleaMarket().getStatus() == MarketStatus.REGISTRATION) {
                if (registered) {
                    // just wait for registration to finish
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        log.error("Exception while waiting", ex);
                        break MAINLOOP;
                    }
                } else {
                    break;
                }
            }
            //log.info("Market Status: {}", marketManager.getCharityFleaMarket().getStatus());
            while (marketManager.getCharityFleaMarket().getStatus() == MarketStatus.OPENED) {
                if (!registered) {
                    break MAINLOOP;
                }
                Random random = new Random();
                boolean auctionRegistered = false;
            //log.debug("{} will try to register auction for {}", customerName, item);
                // wait between 5 and 30 seconds before registering the item
                int randomWaitTime = random.nextInt(26) + 5;
                while (!auctionRegistered) {
                    try {
                        sleep(randomWaitTime * 1000);
                        if (!items.isEmpty()) {
//                            log.debug("Donor {} has still {} items left", customerName, items.size());
                            auctionRegistered = chairman.registerAuction(this, items.peek());
                            if (auctionRegistered) {
                                items.pop();
                            }
                        }

                        if (!auctionRegistered) {
                            randomWaitTime = random.nextInt(6);
                        // If the queue of registered items is full, 
                            // donor waits up to 5 seconds and tries again.
                            sleep(randomWaitTime * 1000);
                        }

                    } catch (InterruptedException ex) {
//                        log.error("Error while waiting for item registration", ex);
                        break MAINLOOP;
                    } catch (Exception ex) {
                        log.error("Other exception", ex);
                    }

                }
            }
        }
//        log.debug("{} says good bye", customerName);
    }

    public Stack<Item> getItems() {
        return items;
    }

}
