package com.example.smukhopadhyay.database.cityTable;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by smukhopadhyay on 10/13/16.
 */
public class CityTableHelper {

    private SQLiteDatabase db;

    public CityTableHelper(SQLiteDatabase db) {
        this.db = db;
    }

    // Method for data into city table
    public boolean insertData(String cityName, String deviation, String latMax, String latMin, String lngMax, String lngMin) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CityTableAttributes.CITY_NAME, cityName);
        contentValues.put(CityTableAttributes.DEVIATION, deviation);
        contentValues.put(CityTableAttributes.LAT_MAX, latMax);
        contentValues.put(CityTableAttributes.LAT_MIN, latMin);
        contentValues.put(CityTableAttributes.LNG_MAX, lngMax);
        contentValues.put(CityTableAttributes.LNG_MIN, lngMin);
        long result = db.insert(CityTableAttributes.TABLE_NAME, null, contentValues);
        if (result == -1) {
            Log.i(CityTableAttributes.LOG, "INSERTION FAILED");
            return false;
        } else {
            Log.i(CityTableAttributes.LOG, "INSERTION SUCCESSFUL");
            return true;
        }
    }

    // Method to get all data from city table and put it in an ArrayList and send back
    public ArrayList<ArrayList<String>> getAllData() {
        Cursor cursor = db.rawQuery(CityTableQueries.GET_ALL_DATA, null);
        if (cursor.getCount() == 0) {
            Log.i(CityTableAttributes.LOG, "NO DATA FOUND");
            return null;
        } else {
            ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
            while (cursor.moveToNext()) {
                ArrayList<String> row = new ArrayList<>();
                row.add(cursor.getString(0));
                row.add(cursor.getString(1));
                row.add(cursor.getString(2));
                res.add(row);
            }
            return res;
        }
    }

    // Given a name of a city, return it's attributes if found, otherwise return null
    public ArrayList<String> getData(String cityName) {
        Cursor cursor = db.rawQuery(CityTableQueries.getCityQuery(cityName), null);
        if (cursor.getCount() == 0) {
            Log.i(CityTableAttributes.LOG, "NO CITY FOUND");
            return null;
        } else {
            ArrayList<String> res = new ArrayList<>();
            while (cursor.moveToNext()) {
                res.add(cursor.getString(0));
                res.add(cursor.getString(1));
                res.add(cursor.getString(2));
                res.add(cursor.getString(3));
                res.add(cursor.getString(4));
                res.add(cursor.getString(5));
                res.add(cursor.getString(6));
            }
            return res;
        }
    }

}
