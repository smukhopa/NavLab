package com.example.smukhopadhyay.database.pathsTable;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.smukhopadhyay.database.intersectionTable.IntersectionTableHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by smukhopadhyay on 10/14/16.
 */
public class PathsTableHelper {

    private SQLiteDatabase db;

    public PathsTableHelper(SQLiteDatabase db) {
        this.db = db;
    }

    // Method for putting data into the Paths table
    public boolean insertData(JSONArray wayArray) {
        try {
            for (int i = 0; i < wayArray.length(); i++) {
                JSONObject wayObject = wayArray.getJSONObject(i);

                // Get the name
                String name = "";
                JSONArray tagArray = wayObject.getJSONArray("tag");
                for (int j = 0; j < tagArray.length(); j++) {
                    JSONObject tagObject = tagArray.getJSONObject(j);
                    if (tagObject.optString("k").equals("name")) {
                        name = tagObject.optString("v");
                    }
                }

                // Get the nodes in the path
                JSONArray ndArray = wayObject.getJSONArray("nd");
                JSONObject ndObjectStart = ndArray.getJSONObject(0);
                String ndRefStart = ndObjectStart.optString("ref");
                IntersectionTableHelper helper = new IntersectionTableHelper(db);
                ArrayList<String> latlngStart = helper.getLatLng(ndRefStart);
                if (latlngStart == null) {
                    latlngStart = new ArrayList<>();
                    latlngStart.add("NOT AVAILABLE");
                    latlngStart.add("NOT AVAILABLE");
                }

                for (int j = 1; j < ndArray.length(); j++) {
                    JSONObject ndObjectFinish = ndArray.getJSONObject(j);
                    String ndRefFinish = ndObjectFinish.optString("ref");
                    ArrayList<String> latlngFinish = helper.getLatLng(ndRefFinish);
                    if (latlngFinish == null) {
                        latlngFinish = new ArrayList<>();
                        latlngFinish.add("NOT AVAILABLE");
                        latlngFinish.add("NOT AVAILABLE");
                    }

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(PathTableAttributes.NAME, name);

                    contentValues.put(PathTableAttributes.START_POINT, ndRefStart);
                    contentValues.put(PathTableAttributes.START_POINT_LAT, latlngStart.get(0));
                    contentValues.put(PathTableAttributes.START_POINT_LNG, latlngStart.get(1));

                    contentValues.put(PathTableAttributes.END_POINT, ndRefFinish);
                    contentValues.put(PathTableAttributes.END_POINT_LAT, latlngFinish.get(0));
                    contentValues.put(PathTableAttributes.END_POINT_LNG, latlngFinish.get(1));

                    // TODO
                    // Get the lenght from the google api
                    String length = "1";
                    contentValues.put(PathTableAttributes.LENGTH, length);

                    // TODO
                    // Not sure how
                    contentValues.put(PathTableAttributes.START_2_END, "TRUE");
                    contentValues.put(PathTableAttributes.END_2_START, "TRUE");

                    contentValues.put(PathTableAttributes.START_2_END_TRAVERSED, "FALSE");
                    contentValues.put(PathTableAttributes.END_2_START_TRAVERSED, "FALSE");

                    contentValues.put(PathTableAttributes.CALC_WEIGHT, length);

                    long result = db.insert(PathTableAttributes.TABLE_NAME, null, contentValues);
                    if (result == -1) {
                        Log.i(PathTableAttributes.LOG, "INSERTION FAILED");
                        return false;
                    } else {
                        Log.i(PathTableAttributes.LOG, "INSERTION SUCCESSFUL");
                    }

                    ndRefStart = ndRefFinish;
                    latlngStart = latlngFinish;

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Method to get the cursor for the database
    public Cursor getCursor() {
        Cursor cursor = db.rawQuery(PathTableQueries.GET_ALL_DATA, null);
        if (cursor.getCount() == 0) {
            Log.i(PathTableAttributes.LOG, "DATABASE EMPTY");
            return null;
        } else {
            return cursor;
        }
    }

}
