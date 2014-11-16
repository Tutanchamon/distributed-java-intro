/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.charityfleamarket.model;

import org.fomfel.charityfleamarket.model.util.RandomNameGenerator;

/**
 *
 * @author Blazej
 */
public class CustomerFactory {
    public static  int MAX_ITEMS_PER_DONOR = 3;
    
    public static Customer generateCustomer(MarketManager marketManager, Chairman chairman, boolean donor) {
        if (donor) {
            return new Donor(RandomNameGenerator.generateRandomName(),
                    marketManager, chairman, MAX_ITEMS_PER_DONOR);
        } else {
            return new Recipient(RandomNameGenerator.generateRandomName(), marketManager, chairman);
        }
    }
}
