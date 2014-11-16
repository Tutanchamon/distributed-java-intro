/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.charityfleamarket.model;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Blazej
 */
public class Chairman extends Thread {

    static final Logger log = LoggerFactory.getLogger(Chairman.class);
    public static final int AUCTIONS_QUEUE_SIZE = 10;
    private final ReentrantLock lock = new ReentrantLock();
    final Condition auctionCondition = lock.newCondition();
    private CharityFleaMarket fleaMarket;
    private Auction currentAuction;
    private boolean anyItemRegistered;

    public Chairman(CharityFleaMarket fleaMarket) {
        this.fleaMarket = fleaMarket;
    }

    public Auction getCurrentAuction() throws InterruptedException {
        lock.lock();
        try {
            while (fleaMarket.getAuctions().peek() == null) {
                auctionCondition.await(5, TimeUnit.SECONDS);
                if (fleaMarket.getAuctions().peek() == null) {
                   //fleaMarket.getMarketManager().shutdownMarket();
                    break;
                }
            }
            return fleaMarket.getAuctions().peek();
        } finally {
            lock.unlock();
        }
    }

    public boolean registerRecipient(Recipient recipient) {
        try {
            if (getCurrentAuction() == null) {
//                log.debug("GetCurrentAuction jest nullem");
            } else {
                boolean registered = getCurrentAuction().addRecipient(recipient);
                if (registered) {
                    log.info("Registering {}", recipient);
                    return registered;
                }
            }
        } catch (NullPointerException ex) {
            log.error("NullPointerException", ex);
        } catch (InterruptedException ex) {
            //log.error("InterruptedException", ex);
        } catch (Exception ex) {
            log.error("General exception", ex);
        }
        return false;
    }

    public boolean registerAuction(Donor donor, Item item) {
        //log.info("Executing registerAuction");
        lock.lock();
        Auction auction = new Auction(donor, item);
        boolean added = false;
        try {
            added = fleaMarket.getAuctions().offer(auction);
            if (added) {
                auctionCondition.signalAll();
                anyItemRegistered = true;
//                log.debug("Successfully added auction for {}", item);
            }
        } catch (Exception ex) {
            log.error("Not added auction for {}", item, ex);
        } finally {
            lock.unlock();
        }
        return added;
    }

    @Override
    public void run() {
        while (fleaMarket.getStatus() == MarketStatus.REGISTRATION) {
            try {
                // do nothing while registration
                sleep(500);
            } catch (InterruptedException ex) {
                log.error("InterruptedException", ex);
            }
        }
        while (fleaMarket.getStatus() == MarketStatus.OPENED) {
            if (fleaMarket.getAuctions().size() > 0) {
                
                int waitTime = fleaMarket.getAuctions().peek().getRegistrationTime();
                try {
                    currentAuction = fleaMarket.getAuctions().peek();
                    // waiting for registration
                    sleep(waitTime * 1000);
                    currentAuction = fleaMarket.getAuctions().poll();
//                    log.debug("Current auction is: {}", currentAuction);
                    // wait for winner
                    sleep(5000);
                    Recipient randomWinner = currentAuction.randomWinner();
                    if (randomWinner == null) {
                        log.info("There is no winner for {}", currentAuction);
                    } else {
                        log.info("Winner for auction {} is {}", currentAuction, randomWinner);
                        //currentAuction.setWinner(randomWinner);
                        randomWinner.addItem(currentAuction.getItem());
                    }
                    for (Recipient recipient : currentAuction.getRecipients()) {
                        recipient.setRegisteredInAuction(false);
                    }
                    log.info("Auction finished - there are {} more auctions in the queue", fleaMarket.getAuctions().size());

                } catch (InterruptedException ex) {
                    log.error("Error while waiting", ex);
                }

            } else if (anyItemRegistered) {
                int totalWaiting = 0;
                while (fleaMarket.getAuctions().size() == 0) {
                    try {
                        if (totalWaiting < 5000) {
//                            log.debug("Chairman counting because no auctions added");
                            sleep(500);
                            totalWaiting += 500;
                        } else {
//                            log.debug("Chairman was waiting for {} and still no auctions", totalWaiting);
                            log.info("No auctions within 5 seconds. Closing the market.");
                            fleaMarket.getMarketManager().shutdownMarket();
                            break;
                        }
                    } catch (InterruptedException ex) {
                        log.error("Exception while waiting", ex);
                    }
                }
            }
        }
        log.info("Chairman says good bye");
    }
}
