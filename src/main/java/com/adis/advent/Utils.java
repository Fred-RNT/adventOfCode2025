package com.adis.advent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Utils {

    public static <T> List<T> parseInputFile(String name, Function<String, T> mapper) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(Utils.class.getResource("/" + name)
                                                                      .toURI()));
            List<T> result = new ArrayList<>();

            for (String line : lines) {
                result.add(mapper.apply(line));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
