package com.adis.advent.d9;

import com.adis.advent.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class D9 {

    List<Point> points;

    public D9(String name) {
        this.points = Utils.parseInputFile(name, this::toPoint);
    }

    public static void main(String[] args) {
        D9 problem = new D9("d9.input");
        problem.resolve();
    }

    private Point toPoint(String s) {
        final var split = s.split(",");
        return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    private void resolve() {
        List<Arete> polygone = new ArrayList<>();

        //on va faire du dessin
        var ptIterator = points.iterator();
        var premier = ptIterator.next();
        var current = premier;
        while (ptIterator.hasNext()) {
            var nextPt = ptIterator.next();
            polygone.add(new Arete(current, nextPt));
            current = nextPt;
        }
        polygone.add(new Arete(current, premier));

        List<Rectangle> rects = new ArrayList<>();
        int size = points.size();
        for (int idxPt1 = 0; idxPt1 < size; idxPt1++) {
            for (int idxPt2 = idxPt1 + 1; idxPt2 < size; idxPt2++) {
                var rec = new Rectangle(points.get(idxPt1), points.get(idxPt2));
                rects.add(rec);
            }
        }
        rects.sort(Comparator.comparing(Rectangle::aire).reversed());

        final var rectangle = rects.stream().sequential().filter(rec -> rec.fitInto(polygone)).findFirst().get();
        System.out.println(rectangle);
        System.out.println(rectangle.aire());
    }

}
