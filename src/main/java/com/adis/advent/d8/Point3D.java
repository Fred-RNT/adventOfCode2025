package com.adis.advent.d8;

public record Point3D(long x, long y, long z) {

    public long distanceA2(Point3D point2){
        //comme racine est croissante on peut retourner la distance au carré
        var res = Math.pow(x - point2.x, 2) + Math.pow(y - point2.y, 2) + Math.pow(z - point2.z, 2);
        if(res <0){
            System.out.println("pb pour "+this+" et "+point2);
        }
        return (long)res;
    }
}
