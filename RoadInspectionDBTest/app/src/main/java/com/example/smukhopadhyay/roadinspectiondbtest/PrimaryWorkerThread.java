package com.example.smukhopadhyay.roadinspectiondbtest;

import android.content.Context;
import android.os.AsyncTask;

import com.example.smukhopadhyay.Algorithms.Djikstra;
import com.example.smukhopadhyay.models.Intersection;
import com.example.smukhopadhyay.models.RoadNetwork;
import com.example.smukhopadhyay.database.DatabaseHelper;
import com.example.smukhopadhyay.mapsAPI.OSMHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Primary thread for executing all commands
 */
public class PrimaryWorkerThread extends AsyncTask {

    private Context mapsActivityContext;
    private DatabaseHelper helper;

    public PrimaryWorkerThread(Context context) {
        mapsActivityContext = context;
    }

    public void setCityConstants(double smallLat, double smallLng, double bigLat, double bigLng) {
        Constants.SMALL_LAT_CITY = smallLat;
        Constants.SMALL_LNG_CITY = smallLng;
        Constants.BIG_LAT_CITY = bigLat;
        Constants.BIG_LNG_CITY = bigLng;
    }

    public void setStartConstants(double startLat, double startLng) {
        Constants.LAT_START = startLat;
        Constants.LNG_START = startLng;
    }

    public void setEndConstants(double endLat, double endLng) {
        Constants.LAT_END = endLat;
        Constants.LNG_END = endLng;
    }


    @Override
    protected Object doInBackground(Object[] params) {

        // Create the DB instance
        helper = new DatabaseHelper(mapsActivityContext);

        // Get the data from the API about the city
        OSMHelper osmHelper = new OSMHelper();
        JSONObject OSMObject = osmHelper.execute(
                Constants.SMALL_LAT_CITY,
                Constants.SMALL_LNG_CITY,
                Constants.BIG_LAT_CITY,
                Constants.BIG_LNG_CITY);

        // Populate the Intersections Table
        osmHelper.populateIntersectionTable(OSMObject, helper);

        // Populate the Paths Table
        osmHelper.populatePathsTable(OSMObject, helper);

        //===========================================================
        // After this, we need to execute the following code every
        // single time we run the app

        // Construct graph
        RoadNetwork network = new RoadNetwork(helper);

        // Now get the closest point from the start
        ArrayList<String> start = osmHelper.findClosestPoint(Constants.LAT_START, Constants.LNG_START);
        network.addPoint(start, Double.toString(Constants.LAT_START), Double.toString(Constants.LNG_START), "1", "start");

        // Get the closest point from the end
        ArrayList<String> end = osmHelper.findClosestPoint(Constants.LAT_END, Constants.LNG_END);
        network.addPoint(end, Double.toString(Constants.LAT_END), Double.toString(Constants.LNG_END), "2", "end");

        // Once graph is populated, get shortest path
        Djikstra.shortestPath(network, network.getIntersectionsMap().get("1"));

        // Now get the route to the destination
        LinkedList<Intersection> route = Djikstra.getRoute(network.getIntersectionsMap().get("2"));

        // Now send the route to the UI
        return route;

        //Test.test();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);



    }
}
