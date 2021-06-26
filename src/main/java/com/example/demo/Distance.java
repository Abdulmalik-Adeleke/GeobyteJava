package com.example.demo;

import com.vividsolutions.jts.geom.Point;

public class Distance {

    public static int GetDistance(Point point1, Point point2)
    {
        double lat1 = point1.getX();
        double lon1 = point1.getY();
        double lat2 = point2.getX();
        double lon2 = point2.getY();

        //COSINE RULE
        int radius = 6371; // DISTANCE OF THE EARTH IN KM
        var latdistance = Math.toRadians(lat2-lat1);
        var londistane = Math.toRadians(lon2-lon1);
        var a = 
            Math.sin(latdistance/2) * Math.sin(latdistance/2) +
            Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(lat1)) *
            Math.sin(londistane/2) * Math.sin(londistane/2);

        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        var distance = c * radius;

        return (int) Math.round(distance);
    }
    
}
