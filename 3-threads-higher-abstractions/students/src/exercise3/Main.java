package exercise3;

import common.html.GazetaHtmlDocument;
import common.html.HtmlDocument;
import java.util.ArrayList;
import java.util.List;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        HtmlDocument rootDocument = new GazetaHtmlDocument("http://wiadomosci.gazeta.pl/");
        Set<String> links = rootDocument.getLinks();
        String wordToFound = "sikorski";

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();

        for (String link : links) {
            WordCounter wc = new WordCounter(link, wordToFound);
            futures.add(executorService.submit(wc));
        }
        int totalFound = 0;
        for (Future<Integer> foundWords : futures) {
            int found = foundWords.get();
            System.out.println("Found " + found + " words");
            totalFound += found;
            
        }
        System.out.println("Totally found " + totalFound + " words");
        executorService.shutdown();

        //System.out.printf("Number of words '%s': %d", wordToFound, numberOfWords);
    }
}
