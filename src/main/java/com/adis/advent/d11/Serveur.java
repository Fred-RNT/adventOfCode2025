package com.adis.advent.d11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Serveur {
    String label;
    List<Serveur> destinations;

    public Serveur(String label) {
        this.label = label;
        destinations = new ArrayList<>();
    }

    public void addDestination(Serveur toAdd){
        destinations.add(toAdd);
    }

    public void info(){
        System.out.printf("Server(%s)-%s%n", label, destinations.stream().map(d->d.label).collect(Collectors.joining(",")));
    }
}
