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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Blazej
 */
public class Auction {
    
    static final Logger log = LoggerFactory.getLogger(Auction.class);

    // PROPERTY STRINGS
    public static final String WINNER = "winner"; // winner property
    public static final String ACTIVE = "active"; // is auction active
    public static final String ADDED = "added"; // recipient added to the auction

    public final int MAX_RECIPENTS;
    private final int registrationTime;
    private boolean active;
    private static final Random random = new Random();
    private final Item item;
    private List<Recipient> recipients;
    private final Donor donor;
    private Recipient winner;

    public Auction(Donor donor, Item item) {
        MAX_RECIPENTS = random.nextInt(10) + 1;
        registrationTime = 5 + random.nextInt(21); // max Time for registration == 25 seconds
        this.item = item;
        recipients = new ArrayList<>();
        this.donor = donor;
    }

    public Recipient randomWinner() {
        if (getRecipients().size() > 0) {
            return getRecipients().get(random.nextInt(getRecipients().size()));
        } else {
            return null;
        }
    }

    public void setWinner(Recipient winner) {
        notifyListeners(WINNER, this.winner, this.winner = winner);
    }

    public void setActive(boolean active) {
        notifyListeners(ACTIVE, this.active, this.active = active);
    }

    private void notifyListeners(String property, Object oldValue, Object newValue) {
        for (PropertyChangeListener name : getRecipients()) {
//            log.debug("this: {}", this);
//            log.debug("property: {}", property);
//            log.debug("oldValue: {}", oldValue);
//            log.debug("newValue: {}", newValue);
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    /**
     * @return the registrationTime
     */
    public int getRegistrationTime() {
        return registrationTime;
    }

    /**
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * @return the donor
     */
    public Donor getDonor() {
        return donor;
    }

    /**
     * @return the winner
     */
    public Recipient getWinner() {
        return winner;
    }

    public boolean addRecipient(Recipient recipient) {
        if (getRecipients().size() < MAX_RECIPENTS) {
            getRecipients().add(recipient);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return item.toString();
    }

    /**
     * @return the recipients
     */
    public List<Recipient> getRecipients() {
        return recipients;
    }

    /**
     * @param recipients the recipients to set
     */
    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }

}
