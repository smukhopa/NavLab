package com.example.smukhopadhyay.database.intersectionTable;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.smukhopadhyay.Algorithms.Distance;
import com.example.smukhopadhyay.database.pathsTable.PathTableAttributes;
import com.example.smukhopadhyay.database.pathsTable.PathTableQueries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by smukhopadhyay on 10/13/16.
 */
public class IntersectionTableHelper {

    private SQLiteDatabase db;

    public IntersectionTableHelper(SQLiteDatabase db) {
        this.db = db;
    }

    // Method for putting data into intersection table
    public boolean insertData(JSONArray nodeArray) {
        try {
            for (int i = 0; i < nodeArray.length(); i++) {
                JSONObject nodeObject = nodeArray.getJSONObject(i);
                String id = nodeObject.optString("id");
                String lat = nodeObject.optString("lat");
                String lng = nodeObject.optString("lon");
                ContentValues contentValues = new ContentValues();
                contentValues.put(IntersectionTableAttributes.ID, id);
                contentValues.put(IntersectionTableAttributes.LAT, lat);
                contentValues.put(IntersectionTableAttributes.LNG, lng);
                long result = db.insert(IntersectionTableAttributes.TABLE_NAME, null, contentValues);
                if (result == -1) {
                    Log.i(IntersectionTableAttributes.LOG, "INSERTION FAILED");
                    return false;
                } else {
                    Log.i(IntersectionTableAttributes.LOG, "INSERTION SUCCESSFUL");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Return the cursor to the entire database
    public Cursor getCursor() {
        Cursor cursor = db.rawQuery(PathTableQueries.GET_ALL_DATA, null);
        if (cursor.getCount() == 0) {
            Log.i(PathTableAttributes.LOG, "DATABASE EMPTY");
            return null;
        } else {
            return cursor;
        }
    }

    // Given a NodeID, return it's lat and lng
    public ArrayList<String> getLatLng(String nodeID) {
        Log.i(PathTableAttributes.LOG, IntersectionTableQueries.getLatLongQuery(nodeID));
        Cursor cursor = db.rawQuery(IntersectionTableQueries.getLatLongQuery(nodeID), null);
        if (cursor.getCount() == 0) {
            Log.i(PathTableAttributes.LOG, "UNABLE TO FIND POINT " + nodeID);
            return null;
        } else {
            ArrayList<String> res = new ArrayList<>();
            while (cursor.moveToNext()) {
                res.add(cursor.getString(1));
                res.add(cursor.getString(2));
            }
            return res;
        }
    }

    // Given a latLng, return the row which is closest to the given latlong
    public ArrayList<String> getClosest(String givanLat, String givenLng) {
        double distance = Integer.MAX_VALUE;

        String resRef = "";
        String resLat = "";
        String resLng = "";

        Cursor cursor = db.rawQuery(IntersectionTableQueries.GET_ALL_DATA, null);
        if (cursor.getCount() == 0) {
            Log.i(PathTableAttributes.LOG, "DATABASE EMPTY");
            return null;
        } else {
            while (cursor.moveToNext()) {
                String ref = cursor.getString(0);
                String lat = cursor.getString(1);
                String lng = cursor.getString(2);
                double res = Distance.getShortestDistance(givanLat, givenLng, lat, lng);
                if (res < distance) {
                    resRef = ref;
                    resLat = lat;
                    resLng = lng;
                }
            }
        }

        ArrayList<String> result = new ArrayList<>();
        result.add(resRef);
        result.add(resLat);
        result.add(resLng);

        return result;
    }
}
