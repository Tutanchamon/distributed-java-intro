/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fomfel.charityfleamarket.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Blazej
 */
public class RandomNameGenerator {
    private static final Random random = new Random();
    public static int nameID = 1;
    private static final List<String> NAMES = Arrays.asList("Andżej",
            "Ardian", "Jarmund", "Gerard", "Donald",
            "Szczepan", "Grzesław", "Sławosław", "Miromir",
            "Ewald", "Janusz", "Burczysław", "Wulfgard",
            "Poncyliusz", "Mietek", "Onufry", "Jądrek",
            "Flawiusz", "Kapcer", "Sędzimir", "Rafalala",
            "Mściwój");
    
    public static String generateRandomName() {
        int randomNumber = random.nextInt(NAMES.size());
        return NAMES.get(randomNumber) + " " + nameID++;
    }
}
