package com.example.smukhopadhyay.mapsAPI;

/**
 * Created by smukhopadhyay on 10/3/16.
 */
public class OSMQueries {

    protected static String get_data_query(double smallLat, double smallLng, double bigLat, double bigLng) {
        return "http://overpass-api.de/api/interpreter?data=(node(" +
                smallLat + "," +
                smallLng + "," +
                bigLat + "," +
                bigLng + ");way(bn);<;);out;";
    }
}
