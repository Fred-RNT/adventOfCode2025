package com.adis.advent.d5;

import com.adis.advent.Tuple;
import com.adis.advent.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var lines = Utils.parseInputFile("d5.input", s -> s);
        Tuple<List<Inventaire>, List<Long>> formattedInput = extraitDonnees(lines);
        System.out.println(process(formattedInput));
    }

    private static long process(Tuple<List<Inventaire>, List<Long>> formattedInput) {
        var aTraiter = formattedInput.item1();

        aTraiter.sort(Comparator.comparing(Inventaire::min));

        final var size = aTraiter.size();

        var inventaireFusionne = new ArrayList<Inventaire>();

        for (int i = 0; i < size; i++) {
            var inventaire = aTraiter.get(i);
            long debut = inventaire.min();
            long fin = inventaire.max();

            if (!inventaireFusionne.isEmpty() && inventaireFusionne.getLast().max() >= fin) {
                //c'est trié
                //donc le debut demarrer forcement apres le debut du dernier, si la fin est avant la fin c'est que l'intervalle est inclus dans le precedent
                continue;
            }

            //on merge ce qu'on peut'
            for(int j = i+1; j < size; j++) {
                var inv = aTraiter.get(j);
                if (inv.min() <= fin) {
                    fin = Math.max(fin, inv.max());
                }
            }

            inventaireFusionne.add(new Inventaire(debut, fin));
        }
        System.out.println("Fusionne " + inventaireFusionne);
        long res = 0;
        for (Inventaire inventaire : inventaireFusionne) {
            res+= inventaire.max()+1-inventaire.min();
        }
        return res;
    }

    private static long getLastMax(ListIterator<Point> iterator) {
        //on vire tt les min
        Point next = null;
        do {
            next = iterator.next();
        } while (next.type().equals("M0"));
        //maintenant tant qu'on a des next on prend le suivant
        while (true) {
            //je suis à la fin
            if (!iterator.hasNext()) {
                break;
            }
            Point tmp = iterator.next();
            //si le suivant est un min alors je suis à la fin
            if (tmp.type().equals("M0")) {
                iterator.previous();
                break;
            } else {
                //sinon j'itere gentiment
                next = tmp;
            }
        }
        return next.valeur();
    }

    private static long getFirstMin(Iterator<Point> iterator) {
        final var next = iterator.next();
        if (next.type().equals("M1")) {
            throw new RuntimeException("Oups");
        }
        return next.valeur();
    }

    private static Tuple<List<Inventaire>, List<Long>> extraitDonnees(List<String> lines) {
        List<Inventaire> inv = new ArrayList<>();
        List<Long> atester = new ArrayList<>();
        var res = new Tuple<>(inv, atester);
        int counter = 0;
        for (; true; counter++) {
            var line = lines.get(counter);
            if (line.isEmpty()) {
                break;
            }
            inv.add(Inventaire.fromInput(line));
        }
        counter++;
        for (; counter < lines.size(); counter++) {
            atester.add(Long.valueOf(lines.get(counter)));
        }
        return res;
    }


}
