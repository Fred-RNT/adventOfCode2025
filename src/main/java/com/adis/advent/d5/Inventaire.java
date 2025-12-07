package com.adis.advent.d5;

import java.util.HashSet;
import java.util.Set;

public record Inventaire(long min, long max) {
    public static Inventaire fromInput(String input) {
        final var split = input.split("-");
        return new Inventaire(Long.parseLong(split[0]),Long.parseLong(split[1]));
    }

    public Set<Long> toSet(){
        var res = new HashSet<Long>();
        for (long i = min; i <= max; i++) {
            res.add(i);
        }
        return res;
    }
}
