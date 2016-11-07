package com.example.smukhopadhyay.mapsAPI;

import android.util.Log;

import com.example.smukhopadhyay.Algorithms.Distance;
import com.example.smukhopadhyay.database.DatabaseHelper;
import com.example.smukhopadhyay.roadinspectiondbtest.MapsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by smukhopadhyay on 10/13/16.
 */
public class OSMHelper {

    public JSONObject execute(double smallLat, double smallLng, double bigLat, double bigLng) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            String query = OSMQueries.get_data_query(smallLat, smallLng, bigLat, bigLng);
            Log.i(OSMAttributes.LOG, query);
            URL url = new URL(query);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";

            // http request is returned and gets stored in the buffer. The return format is XML.
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            JSONObject OSMobject = XML.toJSONObject(buffer.toString());
            Log.i(OSMAttributes.LOG, OSMobject.toString());
            return OSMobject;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void populateIntersectionTable(JSONObject OSMobject, DatabaseHelper helper) {
        try {
            JSONObject osmObject = OSMobject.getJSONObject("osm");
            JSONArray nodeArray = osmObject.getJSONArray("node");
            helper.insertIntersectionTableData(nodeArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void populatePathsTable(JSONObject OSMobject, DatabaseHelper helper) {
        try {
            JSONObject osmObject = OSMobject.getJSONObject("osm");
            JSONArray wayArray = osmObject.getJSONArray("way");
            helper.insertPathsTableData(wayArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Given a lat and long, we are going to make an OSM api call and get the points which are closest to it
    public ArrayList<String> findClosestPoint(double lat, double lng) {

        double smallLat = lat - OSMAttributes.threshold;
        double bigLat = lat + OSMAttributes.threshold;
        double smallLng = lng - OSMAttributes.threshold;
        double bigLng = lng + OSMAttributes.threshold;

        double distance = Double.MAX_VALUE;

        ArrayList<String> res = new ArrayList<>();
        res.add("");
        res.add("");

        try {
            JSONObject jsonObject = execute(smallLat, smallLng, bigLat, bigLng);
            JSONObject osmObject = jsonObject.getJSONObject("osm");
            JSONArray nodeArray = osmObject.getJSONArray("node");

            for (int i = 0; i < nodeArray.length(); i++) {
                JSONObject nodeObject = nodeArray.getJSONObject(i);
                String idNode = nodeObject.optString("id");
                String latNode = nodeObject.optString("lat");
                String lngNode = nodeObject.optString("lon");

                double dis = Distance.getShortestDistance(Double.toString(lat), Double.toString(lng), latNode, lngNode);
                if (dis < distance) {
                    res.set(0, idNode);
                    res.set(1, Double.toString(dis));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

}
