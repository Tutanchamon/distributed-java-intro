/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.supermarket.pointofsale;

import org.fomfel.supermarket.prototype.PointOfSale;

/**
 *
 * @author Blazej
 */
public class PointOfSaleHolder {
    private PointOfSale pointOfSale;
    
    public PointOfSaleHolder() {
        this.pointOfSale = new PointOfSale();
    }
    public PointOfSale getPointOfSale() {
        return pointOfSale;
    }
    
    public void setPointOfSale(PointOfSale pointOfSale) {
        this.pointOfSale = pointOfSale;
    }
}
