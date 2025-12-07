package com.adis.advent.d3;


import com.adis.advent.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class Main extends RuntimeException {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var res = Utils.parseInputFile("d3.input", Main::toRange);
        System.out.println(res.toString());
        var result = res.stream().map(b->b.valeur).reduce(0L, Long::sum);
//        var result = res.stream().map(Interval::getDoublons).flatMap(List::stream).reduce(0L, Long::sum);
                System.out.println(result);
    }

    private static BanqueCellule toRange(String s) {
        return new BanqueCellule(s);
    }
}
