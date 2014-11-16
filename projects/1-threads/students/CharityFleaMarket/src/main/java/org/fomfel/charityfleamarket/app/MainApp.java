/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.charityfleamarket.app;

import org.fomfel.charityfleamarket.model.CharityFleaMarket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Blazej
 */
public class MainApp {

    static final Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        CharityFleaMarket fleaMarket = new CharityFleaMarket();
        fleaMarket.getMarketManager().start();

    }
}
