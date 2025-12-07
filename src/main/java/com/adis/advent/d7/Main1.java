package com.adis.advent.d7;

import com.adis.advent.Utils;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var matrice = Utils.parseInputFile("d7.input", s-> s.toCharArray()).toArray(new char[][]{});
        
        int nbSplit = 0;
        
        for(int ligne = 1 ; ligne < matrice.length ; ligne++){
            for(int colonne = 0 ; colonne<matrice[0].length ; colonne++){
                var courant = matrice[ligne][colonne];
                if(courant == '.'){
                    if(matrice[ligne-1][colonne]=='S' || matrice[ligne-1][colonne]=='|' ){
                        matrice[ligne][colonne]='|';
                    }
                }
                if(courant=='^'){
                    if(matrice[ligne-1][colonne]=='S' || matrice[ligne-1][colonne]=='|' ){
                        nbSplit++;
                        matrice[ligne][colonne-1]='|';
                        matrice[ligne][colonne+1]='|';
                    }
                }
            }
        }

//        afficheMatrice(matrice);
//        System.out.println(nbSplit);
        int startPos = 0;
        for(int i = 0; i < matrice.length ; i++){
            if(matrice[0][i] =='S'){
                startPos = i;
                break;
            }
        }
//        int nbChemins = nombreChemin(matrice, 1,startPos);
//        System.out.println(nbChemins);

        System.out.println(nbCheminsIteratifs(matrice));        
    }

    private static long nbCheminsIteratifs(char[][] matrice) {
        long[][] matriceResultat = new long[matrice.length][matrice[0].length];
        //init de la ligne du bas
        var lastLigne = matrice[matrice.length-1];
        for(int col = 0 ; col < lastLigne.length; col++){
            if(lastLigne[col]=='|'){
                matriceResultat[matrice.length-1][col]=1;
            }
        }
        for(int ligne = matrice.length-2 ; ligne>=0; ligne--) {
            for (int col = 0; col < lastLigne.length; col++) {
                if (matrice[ligne][col] == '|' && matriceResultat[ligne][col]==0) {
                    matriceResultat[ligne][col] = matriceResultat[ligne + 1][col];
                }
            }
            for (int col = 0; col < lastLigne.length; col++) {
                if (matrice[ligne][col] == '^') {
                    matriceResultat[ligne-1][col] = matriceResultat[ligne][col-1]+matriceResultat[ligne][col+1];
                }
            }
        }
        for(int col = 0 ; col < lastLigne.length; col++){
            if(matriceResultat[1][col]!=0){
                return  matriceResultat[1][col];
            }
        }
        throw new RuntimeException("pb");
    }

    private static int nombreChemin(char[][] matrice, int ligne, int colonne) {
        if(ligne == matrice.length-1){
            return 1;
        }
        if(matrice[ligne+1][colonne]=='^'){
            return nombreChemin(matrice, ligne+1, colonne-1)+nombreChemin(matrice, ligne+1, colonne+1);
        }else{
            // '|'
            return nombreChemin(matrice, ligne+1, colonne);
        }
        
    }

    private static void afficheMatrice(char[][] matrice) {
        for(int i = 0 ; i < matrice.length;i++){
            System.out.println(new String(matrice[i]));
        }
    }
}
