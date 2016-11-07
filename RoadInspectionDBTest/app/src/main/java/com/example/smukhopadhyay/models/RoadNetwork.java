package com.example.smukhopadhyay.models;

import android.database.Cursor;

import com.example.smukhopadhyay.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by smukhopadhyay on 10/18/16.
 */
public class RoadNetwork {
    private List<Intersection> intersections;
    private List<Path> paths;
    private HashMap<String, Intersection> intersectionsMap;

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public HashMap<String, Intersection> getIntersectionsMap() {
        return intersectionsMap;
    }

    public RoadNetwork(DatabaseHelper helper) {
        intersections = new ArrayList<>();
        paths = new ArrayList<>();
        intersectionsMap = new HashMap<>();
        buildRoadNetwork(helper);
    }

    /*
    For testing only!
    Remove later
     */
    public RoadNetwork(List<Intersection> intersections, List<Path> paths) {
        this.intersections = intersections;
        this.paths = paths;
    }

    private void buildRoadNetwork(DatabaseHelper helper) {
        initializeIntersections(helper);
        initializePaths(helper);
    }

    private void initializeIntersections(DatabaseHelper helper) {
        Cursor cursor = helper.getIntersectionsTableCursor();
        while (cursor.moveToNext()) {
            String ID = cursor.getString(0);
            String lat = cursor.getString(1);
            String lng = cursor.getString(2);
            Intersection intersection = new Intersection(ID, lat, lng);
            intersections.add(intersection);
            intersectionsMap.put(ID, intersection);
        }
    }

    private void initializePaths(DatabaseHelper helper) {
        Cursor cursor = helper.getPathsTableCursor();
        while (cursor.moveToNext()) {
            String ID = cursor.getString(0);
            String startID = cursor.getString(2);
            Intersection start = intersectionsMap.get(startID);
            String endID = cursor.getString(5);
            Intersection end = intersectionsMap.get(endID);
            double weight = Double.parseDouble(cursor.getString(13));

            Path path = new Path(ID, start, end, weight);

            paths.add(path);
        }
    }

    // Used for adding an additional point to the network
    public void addPoint(ArrayList<String> arr, String lat, String lng, String id, String pathID) {
        Intersection intersection = new Intersection(id, lat, lng);
        intersections.add(intersection);
        intersectionsMap.put(id, intersection);
        Intersection alreadyInGraph = intersectionsMap.get(arr.get(0));
        Path path = new Path(pathID, intersection, alreadyInGraph, Double.parseDouble(arr.get(2)));
        paths.add(path);
    }


}
