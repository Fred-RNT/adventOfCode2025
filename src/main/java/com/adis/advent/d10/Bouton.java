package com.adis.advent.d10;

public class Bouton {
    int[] switches;

    public Bouton(String input){
        var tmp = input.substring(1, input.length()-1).split(",");
        switches = new int[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            switches[i] = Integer.parseInt(tmp[i]);
        }
    }

    public boolean canSwitch(int aSwitcher) {
        for (int aSwitch : switches) {
            if(aSwitch == aSwitcher){
                return true;
            }
        }
        return false;
    }
}
