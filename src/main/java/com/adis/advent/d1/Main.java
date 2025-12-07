package com.adis.advent.d1;

import com.adis.advent.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        List<Integer> res = Utils.parseInputFile("d1.input",
                                                 s -> transform(s));      //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        int numOfZero = 0;
        int position = 50;
        for (Integer clickCourant : res) {

            var pInit = position;
            var tmp = position + clickCourant;
            var numOfZeroCalcule = Math.abs(tmp / 100);
            if (pInit!=0 && tmp * position <= 0)
                numOfZeroCalcule++;
            position = tmp % 100;
            if (numOfZeroCalcule > 0)
                System.out.printf("origine:%d, increment:%d, nbZerocalculé:%d%n",
                                  pInit,
                                  clickCourant,
                                  numOfZeroCalcule);
            numOfZero += numOfZeroCalcule;
        }
        System.out.println(numOfZero);
    }

    private static Integer transform(String s) {
        if (s.startsWith("L")) {
            return Integer.parseInt(s.substring(1));
        }
        return -1 * Integer.parseInt(s.substring(1));
    }
}
