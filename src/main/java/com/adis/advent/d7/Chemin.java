package com.adis.advent.d7;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chemin {
    List<Point> points = new ArrayList();
    
    public void add(Point toAdd){
        points.add(toAdd);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Chemin chemin = (Chemin) o;
        return Objects.equals(points, chemin.points);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(points);
    }
}
