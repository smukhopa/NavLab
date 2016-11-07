package com.example.smukhopadhyay.Algorithms;

/**
 * Created by smukhopadhyay on 10/18/16.
 */

// Reference: http://jonisalonen.com/2014/computing-distance-between-coordinates-can-be-simple-and-fast/
public class Distance {

    // Given a pair of lat and long, return the distance between them
    public static double getShortestDistance(String lat1, String lng1, String lat2, String lng2) {
        double deglen = 110.25;
        double x = Double.parseDouble(lat1) - Double.parseDouble(lat2);
        double y = (Double.parseDouble(lng1) - Double.parseDouble(lng2)) * Math.cos(Double.parseDouble(lat1));
        return (deglen * Math.sqrt(x*x + y*y));
    }
}
