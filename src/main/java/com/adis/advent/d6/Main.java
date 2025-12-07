package com.adis.advent.d6;

import com.adis.advent.Tuple;
import com.adis.advent.Utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        //on rajoute un espace à la fin comme ca tt les groupes sont identiques
        char[][] input = new char[][]{};
        input = Utils.parseInputFile("d6.input", s -> (s).toCharArray()).toArray(input);

        long total = 0;
        boolean finished = false;
        int position = 0;
        while(!finished){
            Tuple<Integer, Long> tmp = process(input, position);
            position = tmp.item1();
            total+=tmp.item2();
            finished = position == -1;
        }

        System.out.println(total);
    }

    private static Tuple<Integer, Long> process(char[][] input, int position) {
        char[] operatorLine = input[input.length-1];
        char operator = operatorLine[position];
        int tailleBlock = 1;
        for(int j = position+1; j < operatorLine.length ; j++){
            if(operatorLine[j]!=' '){
                tailleBlock--;
                break;
            }else{
                tailleBlock++;
            }
        }
        var newPosition = position+tailleBlock == operatorLine.length?-1:position+tailleBlock+1;

        long subtotal = operator=='*' ? 1 : 0;

        for(int colonne = 0 ; colonne < tailleBlock ; colonne++){
            long tmp = 0;
            //on lit en colonne
            for(int ligne = 0 ; ligne < input.length-1 ; ligne++){
                if(input[ligne][colonne+position]!=' '){
                    tmp = 10*tmp+(input[ligne][colonne+position]-'0');
                }
            }
            if(operator=='*'){
                subtotal*=tmp;
            }else{
                subtotal+=tmp;
            }
        }


        return new Tuple(newPosition,subtotal);
    }
}
