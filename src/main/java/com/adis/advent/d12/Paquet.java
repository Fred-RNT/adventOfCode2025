package com.adis.advent.d12;

public class Paquet {
    char[][] forme;
    int aire;
    public Paquet(String... lines){
        forme = new char[3][3];
        int ligne = 0;
        for (String line : lines) {
            forme[ligne++] = line.toCharArray();
        }
        calculeAire();
    }

    private void calculeAire() {
        int tmp = 0;
        for(int ligne = 0 ; ligne < forme.length; ligne++){
            for(int colonne = 0 ; colonne < forme[0].length ; colonne++){
                if(forme[ligne][colonne] == '#'){
                    tmp++;
                }
            }
        }
        aire = tmp;
    }
}
