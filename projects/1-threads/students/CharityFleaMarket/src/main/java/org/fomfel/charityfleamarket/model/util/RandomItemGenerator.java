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
public class RandomItemGenerator {
    private static final Random random = new Random();
    private static int itemID = 1;
    private static final List<String> NAMES = Arrays.asList("Telewizor \"RUBIN\"",
            "Komplet kapsli", "Kolejka elektryczna",
            "Dżinsy \"ODRA\"", "Popiersie Lenina",
            "Kapelusz kowbojski", "Skarpetki sing-sing",
            "Matrioszka", "Marynowane grzybki",
            "Szczebel z drabiny, która się przyśniła św. Jakubowi",
            "Kolekcja znaczków z Turkmenistanu",
            "Wieczne pióro", "Flaszka niewypijka",
            "Powietrze z pompki od materaca",
            "Harmonijka ustna", "Słoik keczupu",
            "Airbus A380", "Kilof", "Lampka USB z Kauflandu",
            "Koło zapasowe do stara", "Silnik od Zastawy",
            "Magnetofon szpulowy", "Radio \"LENA\"");
    
    public static String generateRandomName() {
        int randomNumber = random.nextInt(NAMES.size());
        return NAMES.get(randomNumber) + " " + itemID++;
    }
}
