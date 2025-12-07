package com.adis.advent.d2;

import java.util.ArrayList;
import java.util.List;

public class Interval {
    long min;
    long max;
    List<Long> doublons = new ArrayList<>();

    public Interval(long min, long max) {
        this.min = min;
        this.max = max;
        process();
    }

    public static Interval fromRange(String item) {
        final var split = item.split("-");
        return new Interval(Long.parseLong(split[0]), Long.parseLong(split[1]));
    }

    @Override
    public String toString() {
        return "Interval{" +
                "min=" + min +
                ", max=" + max +
                ", doublons=" + doublons +
                '}';
    }

    public List<Long> getDoublons() {
        return doublons;
    }

    private void process() {
        for (long l = min; l <= max; l++) {
            if (chercheRepetition(l)) {
                doublons.add(l);
            }
        }
    }

    private boolean chercheRepetition(long l) {
        String ls = String.valueOf(l);
        int length = ls.length();

        for (int longeurCourante = 1; longeurCourante <= length / 2; longeurCourante++) {
            if(length%longeurCourante!=0){
                continue;
            }
            int nbItem = length/longeurCourante;
            List<String> toTest = new ArrayList<>();
            for(int tmp = 0 ; tmp < nbItem ; tmp++){
                toTest.add(ls.substring(tmp*longeurCourante, (tmp+1)*longeurCourante));
            }
            if(toTest.stream().distinct().count() == 1){
                return true;
            }
        }
        return false;
    }
}
