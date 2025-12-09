package com.adis.advent.d9;

import java.util.List;
import java.util.Objects;

public final class Rectangle {
    private final Point p1;
    private final Point p2;


    long xMin, xMax, yMin, yMax;

    public Rectangle(Point p1, Point p2) {
        xMin = Math.min(p1.x(), p2.x());
        xMax = Math.max(p1.x(), p2.x());
        yMin = Math.min(p1.y(), p2.y());
        yMax = Math.max(p1.y(), p2.y());
        this.p1 = p1;
        this.p2 = p2;
    }

    long aire() {
        return (long) (Math.abs(p1.x() - p2.x()) + 1) * (Math.abs(p1.y() - p2.y()) + 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Rectangle) obj;
        return Objects.equals(this.p1, that.p1) &&
                Objects.equals(this.p2, that.p2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p1, p2);
    }

    @Override
    public String toString() {
        return "Rectangle[" +
                "p1=" + p1 + ", " +
                "p2=" + p2 + ']';
    }

    public boolean fitInto(List<Arete> polygone) {
        //par contruction on sait que les 2 pts sont dans le polygone
        //on check si les 2 autres le sont
        Point pt3 = new Point(p1.x(), p2.y());
        Point pt4 = new Point(p2.x(), p1.y());

        System.out.println(">>>Rectangle(" + aire() + ")");
        var res = isInside(pt3, polygone) & isInside(pt4, polygone);
        System.out.println("<<<");
        return res;
    }

    private boolean isInside(Point pt, List<Arete> polygone) {
        //on compte le nb d'intersection entre le segment [(0,pt.y),pt] et les aretes. Si c'est pair, on est dedans, si c'est impair on est dehors
        //on simplifie le pb car on n'a aucun pt en x = 0, donc on est sur que (0,pt.y) est bien en dehors du polygone

        int intersection = 0;
        for (Arete arete : polygone) {
            if (pt.equals(arete.point1()) || pt.equals(arete.point2())) {
                System.out.println(pt + "dans forme");
                return true;
            } else {
                var inter =  (intersecte(arete, pt));
                if(inter==-1){
                    System.out.println(pt + "dans forme");
                    return true;
                }
                intersection+=inter;
            }
        }

        System.out.println(pt + "intersection=" + intersection);
        return intersection % 2 == 1;
    }

    //-1 = dans le segment, 0 = pas d'intersection, 1 = intersection
    private int intersecte(Arete arete, Point pt) {
        //si ligne horizontale
        if (arete.horizontal()) {
            if (pt.y() != arete.point1().y()) {
                return 0;
            }

            if(pt.x() >= arete.minX() && pt.x()<=arete.maxX()){
                return -1;
            }
            return pt.x()<arete.minX()?0: 1;
        } else {
            //ligne verticale
            if (arete.minY() > pt.y() || arete.maxY() < pt.y()) {
                return 0;
            }
            //sinon le y est entre les 2, il faut voir si on est sur la ligne ou si x >= xmax
            
            if(pt.x() == arete.maxX()){
                return -1;
            }
            return pt.x() > arete.maxX()?1:0;
        }
    }
}
