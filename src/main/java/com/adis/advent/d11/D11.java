package com.adis.advent.d11;

import com.adis.advent.Tuple;
import com.adis.advent.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class D11 {

    Map<String, Serveur> baieDeStockage;

    public D11(String input) {
        var toProcess = Utils.parseInputFile(input, this::parseLigne);
        baieDeStockage = new HashMap<>();
        for (Tuple<String, List<String>> t : toProcess) {
            var serverName = t.item1();
            baieDeStockage.put(serverName, new Serveur(serverName));
        }
        for (Tuple<String, List<String>> t : toProcess) {
            var liaisons = t.item2();
            var serverName = t.item1();
            var serveur = baieDeStockage.get(serverName);
            for (String liaison : liaisons) {
                if (baieDeStockage.get(liaison) == null) {
                    System.out.println("attention on va creer le serveur " + liaison);
                    baieDeStockage.put(liaison, new Serveur(liaison));
                }
                serveur.addDestination(baieDeStockage.get(liaison));
            }
        }

    }

    public static void main(String[] args) {
        D11 problem = new D11("d11.input");
        problem.resolve();
    }

    private Tuple<String, List<String>> parseLigne(String s) {
        final var split = s.split(" ");
        List<String> liaisons = new ArrayList<>(Arrays.asList(split).subList(1, split.length));
        var serverName = split[0].substring(0, split[0].length() - 1);
        return new Tuple<>(serverName, liaisons);
    }

    private void resolve() {
        final var debut = baieDeStockage.get("svr");
        final var fin = baieDeStockage.get("out");

        final var fft = baieDeStockage.get("fft");
        final var dac = baieDeStockage.get("dac");

        var cheminsTotaux = cheminsPassantPar(debut, fin, fft, dac)+cheminsPassantPar(debut, fin,dac, fft);

        System.out.println(cheminsTotaux);
    }

    long cheminsPassantPar(Serveur debut, Serveur fin, Serveur intermediaire1, Serveur intermediaire2){
        Map<Serveur, Long> memo = new HashMap<>();
        var startVersIntermediaire1 = traverseAcyclique(debut, intermediaire1, memo, intermediaire2);
        memo = new HashMap<>();
        var intermediaire21VersIntermediaire2 = traverseAcyclique(intermediaire1, intermediaire2, memo, fin);
        memo = new HashMap<>();
        var intermediaire2VersFin = traverseAcyclique(intermediaire2, fin, memo, intermediaire1);

        return startVersIntermediaire1 * intermediaire21VersIntermediaire2 * intermediaire2VersFin;

    }


    private long traverseAcyclique(Serveur start, Serveur end, Map<Serveur, Long> coutsCalcules, Serveur pointInterdit) {
        if (start.equals(end)) {
            return 1;
        }

        if (coutsCalcules.containsKey(start)) {
            return coutsCalcules.get(start);
        }

        long compteur = 0;
        for (Serveur s : start.destinations) {
            if (pointInterdit.label.equals(s.label)) {
                continue;
            }
            compteur += traverseAcyclique(s, end, coutsCalcules, pointInterdit);
        }

        coutsCalcules.put(start, compteur);
        return compteur;
    }

}
