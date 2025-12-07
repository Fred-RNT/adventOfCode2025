package com.adis.advent.d3;

public class BanqueCellule {
    long valeur;
    public BanqueCellule(String s) {
        valeur = getNthMax(0, 1, 0, s);
    }

    private long getNthMax(int positionDepart, int nth, long previousValue, String input){
//        System.out.println("getNthMax("+positionDepart+","+nth+","+previousValue+","+input+")");
        if(nth==13){
            return previousValue;
        }
        int posNth = -1;
        long valeur = -1;
        for (int i = positionDepart; i < input.length()-(12-nth); i++) {
            var current = Long.parseLong(String.valueOf(input.charAt(i)));
            if(current>valeur){
                valeur = current;
                posNth = i;
            }
        }

        return getNthMax(posNth+1, nth+1, 10*previousValue+valeur, input);

    }

    @Override
    public String toString() {
        return "BanqueCellule{" +
                "valeur=" + valeur +
                '}';
    }
}
