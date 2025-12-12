package com.adis.advent.d12;

public class Region {
    int longueur;
    int largeur;

    int[] contenuVoulu;

    public Region(int longueur, int largeur, int nbPaquets) {
        this.longueur = longueur;
        this.largeur = largeur;
        contenuVoulu = new int[nbPaquets];
    }
}
