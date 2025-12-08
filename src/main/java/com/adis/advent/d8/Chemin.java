package com.adis.advent.d8;

import java.util.HashSet;
import java.util.Set;

public class Chemin {
    Set<Integer> points;

    public Chemin() {
        points = new HashSet<>();
    }

    public boolean canAdd(int idx1, int idx2){
        return points.contains(idx1) || points.contains(idx2);
    }

    public void add(int idx1, int idx2){
        points.add(idx1);
        points.add(idx2);
    }

    public void addChemin(Chemin ch2){
        points.addAll(ch2.points);
    }

    @Override
    public String toString() {
        return "Chemin{" +
                "points=" + points +
                '}';
    }
}
