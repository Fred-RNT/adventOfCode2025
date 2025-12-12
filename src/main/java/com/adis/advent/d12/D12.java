package com.adis.advent.d12;

import com.adis.advent.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class D12 {

    List<Paquet> paquets;
    List<Region> regions;

    public D12(String s) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(Utils.class.getResource("/" + s).toURI()));
            final var iterator = lines.listIterator();
            parsePaquets(iterator);
            parseRegions(iterator);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        D12 problem = new D12("d12.input");
        problem.resolve();
    }

    private void parseRegions(ListIterator<String> iterator) {
        regions = new ArrayList<>();
        while (iterator.hasNext()) {
            var splitted = iterator.next().split(" ");
            var dimensions = splitted[0].substring(0, splitted[0].length() - 1).split("x");
            Region toAdd = new Region(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]), paquets.size());
            for (int i = 0; i < toAdd.contenuVoulu.length; i++) {
                toAdd.contenuVoulu[i] = Integer.parseInt(splitted[i + 1]);
            }
            regions.add(toAdd);
        }
    }

    private void parsePaquets(ListIterator<String> iterator) {
        paquets = new ArrayList<>();
        while (true) {
            final var premiereLigne = iterator.next();
            if (premiereLigne.contains("x")) {
                iterator.previous();
                break;
            }
            //1ere ligne osef, c'est nle numero du paquet
            final var line1 = iterator.next();
            final var line2 = iterator.next();
            final var line3 = iterator.next();
            paquets.add(new Paquet(line1, line2, line3));
            iterator.next();
        }
    }

    private void resolve() {
        System.out.println(regions.stream().filter(r -> peutContenir(r, paquets)).count());
    }

    private boolean peutContenir(Region r, List<Paquet> paquets) {
        var aireRegion = r.longueur * r.largeur;
        var aireRequise = 0;
        for (int i = 0; i < r.contenuVoulu.length; i++) {
            aireRequise += r.contenuVoulu[i] * paquets.get(i).aire;
        }
        return aireRegion >= aireRequise;
    }
}
