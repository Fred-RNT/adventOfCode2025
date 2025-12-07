package com.adis.advent.d4;

import com.adis.advent.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var res = Utils.parseInputFile("d4.input", Main::toRange);
        var matrix = matrice(res);
        var input = new Matrice();
        input.content = matrix;
        int totalRetirable = 0;
        do {
            input = nbDeplacable(input);
            totalRetirable += input.retirable;
        } while (input.retirable != 0);
        System.out.println("Total retirable: " + totalRetirable);
    }

    private static Matrice nbDeplacable(Matrice input) {
        var resultat = new Matrice();
        resultat.content = new char[input.content.length][];
        var matrix = input.content;
        for (int i = 0; i < matrix.length; i++) {
            resultat.content[i] = new char[matrix[i].length];
            for (int j = 0; j < matrix[i].length; j++) {
                resultat.content[i][j] = matrix[i][j];

                if (matrix[i][j] == '.') {
                    continue;
                }
                final var nbAdjacent = nbAdjacent(matrix, i, j);
                //System.out.printf("nbAdjacent(%d,%d)=%d%n", i, j, nbAdjacent);
                if (nbAdjacent < 4) {
                    resultat.retirable++;
                    resultat.content[i][j] = '.';
                }
            }
        }
        return resultat;
    }

    private static int nbAdjacent(char[][] matrix, int i, int j) {
        int res = 0;
        for (int ii = -1; ii <= 1; ii++) {
            for (int jj = -1; jj <= 1; jj++) {
                var tmpi = i + ii;
                var tmpj = j + jj;
                if (tmpi < 0 || tmpj < 0 || tmpi > matrix.length - 1 || tmpj > matrix[0].length - 1) {
                    continue;
                }
                if (ii == 0 && jj == 0) {
                    continue;
                }
                if (matrix[tmpi][tmpj] == '@') {
                    res++;
                }
            }
        }
        return res;
    }

    private static char[] toRange(String s) {
        return s.toCharArray();
    }

    static char[][] matrice(List<char[]> input) {
        int nbLigne = input.size();
        char[][] matrice = new char[nbLigne][];
        for (int i = 0; i < nbLigne; i++) {
            matrice[i] = input.get(i);
        }
        return matrice;
    }
}
