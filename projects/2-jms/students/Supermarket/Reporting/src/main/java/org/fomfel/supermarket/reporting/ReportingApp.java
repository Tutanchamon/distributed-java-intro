/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.supermarket.reporting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Blazej
 */
public class ReportingApp {
    public static String EXIT = "exit";
    
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String in = "";
        System.out.println("Reports will be shown after every sale...");
        while (!in.equalsIgnoreCase(EXIT)) {
            try {
                System.out.println("Type EXIT to leave the app...");
                in = bufferedReader.readLine();
                
            } catch (IOException ex) {
                Logger.getLogger(ReportingApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

