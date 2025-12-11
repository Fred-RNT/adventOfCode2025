package com.adis.advent.d11;

import com.adis.advent.Tuple;
import com.adis.advent.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class D11 {

    Map<String, Server> machines;

    public D11(String input) {
        var toProcess = Utils.parseInputFile(input, this::parseLigne);
        machines = new HashMap<>();
        for (Tuple<String, List<String>> t : toProcess) {
            var serverName = t.item1();
            machines.put(serverName, new Server(serverName));
        }
        for (Tuple<String, List<String>> t : toProcess) {
            var liaisons = t.item2();
            var serverName = t.item1();
            var serveur = machines.get(serverName);
            for (String liaison : liaisons) {
                if (machines.get(liaison) == null) {
                    System.out.println("attention on va creer le serveur " + liaison);
                    machines.put(liaison, new Server(liaison));
                }
                serveur.addDestination(machines.get(liaison));
            }
        }

    }

    public static void main(String[] args) {
        D11 problem = new D11("d11.input");
        problem.resolve();
    }

    private Tuple<String, List<String>> parseLigne(String s) {
        final var split = s.split(" ");
        List<String> liaisons = new ArrayList<>();
        liaisons.addAll(Arrays.asList(split).subList(1, split.length));
        var server = split[0].substring(0, split[0].length() - 1);
        return new Tuple<>(server, liaisons);
    }

    private void resolve() {
        final var debut = machines.get("svr");
        final var fin = machines.get("out");

        Map<Server, Long> memo = new HashMap<>();
        final var fft = machines.get("fft");
        final var dac = machines.get("dac");

        var debutVersFft = traverseAcyclique(debut, fft, memo, dac);
        memo = new HashMap<>();
        var fftVersDac = traverseAcyclique(fft, dac, memo, fin);
        memo = new HashMap<>();
        var dacVersFin = traverseAcyclique(dac, fin, memo, fft);

        var cout1 = debutVersFft * fftVersDac * dacVersFin;

        memo = new HashMap<>();
        var debutVersDac = traverseAcyclique(debut, dac, memo, fft);
        memo = new HashMap<>();
        var dacVersFft = traverseAcyclique(dac, fft, memo, fin);
        memo = new HashMap<>();
        var fftVersFin = traverseAcyclique(fft, fin, memo, dac);

        var cout2 = debutVersDac * dacVersFft * fftVersFin;

        System.out.println(cout1 + cout2);
    }


    private long traverseAcyclique(Server start, Server end, Map<Server, Long> coutsCalcules, Server pointInterdit) {
        if (start.equals(end)) {
            return 1;
        }

        if (coutsCalcules.containsKey(start)) {
            return coutsCalcules.get(start);
        }

        long compteur = 0;
        for (Server s : start.destinations) {
            if (pointInterdit.label.equals(s.label)) {
                continue;
            }
            compteur += traverseAcyclique(s, end, coutsCalcules, pointInterdit);
        }

        coutsCalcules.put(start, compteur);
        return compteur;
    }

}
