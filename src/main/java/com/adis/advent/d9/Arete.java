package com.adis.advent.d9;

public record Arete(Point point1, Point point2) {
    boolean horizontal(){
        return point1.y()==point2.y();
    }
    int minX(){
        return Math.min(point1.x(), point2.x());
    }
    int maxX(){
        return Math.max(point1.x(), point2.x());
    }
    int minY(){
        return Math.min(point1.y(), point2.y());
    }
    int maxY(){
        return Math.max(point1.y(), point2.y());
    }
}
