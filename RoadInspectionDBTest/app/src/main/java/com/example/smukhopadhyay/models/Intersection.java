package com.example.smukhopadhyay.models;

/**
 * Created by smukhopadhyay on 10/18/16.
 */
public class Intersection {

    private String ID;
    private String lat;
    private String lng;

    public Intersection(String ID, String lat, String lng) {
        this.ID = ID;
        this.lat = lat;
        this.lng = lng;
    }

    public String getID() {
        return ID;
    }
    public String getLat() {
        return lat;
    }
    public String getLng() {
        return lng;
    }
}
