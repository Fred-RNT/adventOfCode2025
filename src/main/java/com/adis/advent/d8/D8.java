package com.adis.advent.d8;

import com.adis.advent.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class D8 {
    List<Point3D> points;

    public D8(String name) {
        this.points = Utils.parseInputFile(name, s -> toPoint3D(s));
    }

    public static void main(String[] args) {
        D8 d8 = new D8("d8.input");
        d8.resolve();
    }

    private static Chemin newChemin(DistancePts candidat) {
        Chemin toAdd = new Chemin();
        toAdd.add(candidat.indexPt1(), candidat.indexPt2());
        return toAdd;
    }

    private static int mergeChemins(List<Integer> indexes, List<Chemin> chemins) {
        if (indexes.size() > 2) {
            throw new RuntimeException("Oups l'algo marche pas comme prévu");
        }
        //il faut merger
        indexes.sort(Integer::compareTo);
        Chemin merged = chemins.get(indexes.getFirst());
        int index1 = indexes.getLast();
        merged.addChemin(chemins.get(index1));
        chemins.remove(index1);

        //on retourne la taille du chemin mergé, ca sert à tester la condition de fin
        return merged.points.size();
    }

    private Point3D toPoint3D(String s) {
        final var split = s.split(",");
        return new Point3D(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2]));
    }

    private void resolve() {
        List<DistancePts> distances = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                distances.add(new DistancePts(i, j, points.get(i).distanceA2(points.get(j))));
            }
        }
        //on calcule les distances
        distances.sort(Comparator.comparing(DistancePts::dist2));

        System.out.println(distances);
        //init
        List<Chemin> chemins = new ArrayList<>();
        chemins.add(newChemin(distances.getFirst()));

        int nbItemsAFusionner = points.size();

        DistancePts dernier = null;
        int nieme = 0;
        while (dernier == null) {
            nieme++;
            var candidat = distances.get(nieme);
            List<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < chemins.size(); i++) {
                if (chemins.get(i).canAdd(candidat.indexPt1(), candidat.indexPt2())) {
                    indexes.add(i);
                }
            }
            //aucun ajout
            if (indexes.isEmpty()) {
                chemins.add(newChemin(candidat));
            } else if (indexes.size() == 1) {
                //1 seul ajout
                var aTraiter = chemins.get(indexes.getFirst());
                aTraiter.add(candidat.indexPt1(), candidat.indexPt2());
                if (aTraiter.points.size() == nbItemsAFusionner) {
                    dernier = candidat;
                }
            } else {
                //2 ajouts -> on doit merger les chemins avec les points communs
                var mergedSize = mergeChemins(indexes, chemins);
                if (mergedSize == nbItemsAFusionner) {
                    dernier = candidat;
                }
            }
        }

        System.out.println(points.get(dernier.indexPt1()).x() * points.get(dernier.indexPt2()).x());
    }
}
