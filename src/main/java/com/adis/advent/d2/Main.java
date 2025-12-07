package com.adis.advent.d2;

import com.adis.advent.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class Main extends RuntimeException {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var res = Utils.parseInputFile("d2.input", Main::toRange).getFirst();
        var result = res.stream().map(Interval::getDoublons).flatMap(List::stream).reduce(0L, Long::sum);
        System.out.println(result);
    }

    private static List<Interval> toRange(String s) {
        return Arrays.stream(s.split(",")).map(item-> Interval.fromRange(item)).toList();
    }
}
