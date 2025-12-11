package com.adis.advent.d11;

import com.adis.advent.Tuple;
import com.adis.advent.Utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Deque<Server> chemin = new ArrayDeque<>();
        chemin.add(machines.get("svr"));
        var res = traverse(machines.get("svr"), machines.get("out"), chemin);
        System.out.println(res);
    }

    private int traverse(Server start, Server end, Deque<Server> chemin) {
        if (start.equals(end)) {
            if (chemin.contains(machines.get("fft")) && chemin.contains(machines.get("dac"))) {
                System.out.println(chemin.stream().map(s -> s.label).collect(Collectors.joining(",")));
                return 1;
            } else {
                return 0;
            }
        }
        if (start.destinations.isEmpty()) {
            return 0;
        }
        var compteur = 0;
        for (Server s : start.destinations) {
            if (chemin.contains(s)) {
                continue;
            }
            chemin.add(s);
            compteur += traverse(s, end, chemin);
            chemin.remove(s);
        }
        return compteur;
    }
}
