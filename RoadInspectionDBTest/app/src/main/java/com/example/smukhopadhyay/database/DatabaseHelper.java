package com.example.smukhopadhyay.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.smukhopadhyay.database.cityTable.CityTableHelper;
import com.example.smukhopadhyay.database.cityTable.CityTableQueries;
import com.example.smukhopadhyay.database.intersectionTable.IntersectionTableHelper;
import com.example.smukhopadhyay.database.intersectionTable.IntersectionTableQueries;
import com.example.smukhopadhyay.database.pathsTable.PathTableQueries;
import com.example.smukhopadhyay.database.pathsTable.PathsTableHelper;
import com.example.smukhopadhyay.roadinspectiondbtest.MapsActivity;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by smukhopadhyay on 10/13/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    // Call the constructor to create the DB
    public DatabaseHelper(Context context) {
        super(context, DatabaseAttributes.DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create the city table
        db.execSQL(CityTableQueries.CREATE_TABLE_QUERY);
        Log.i(DatabaseAttributes.LOG, "CREATED CITY_TABLE");

        // Create the intersection table
        db.execSQL(IntersectionTableQueries.CREATE_TABLE_QUERY);
        Log.i(DatabaseAttributes.LOG, "CREATED INTERSECTION_TABLE");

        // Create the paths table
        db.execSQL(PathTableQueries.CREATE_TABLE_QUERY);
        Log.i(DatabaseAttributes.LOG, "CREATED PATHS_TABLE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Delete all tables and create a new ones

        // Delete the city table
        db.execSQL(CityTableQueries.DROP_TABLE_QUERY);

        // Delete the intersection table
        db.execSQL(IntersectionTableQueries.DROP_TABLE_QUERY);

        onCreate(db);
    }

    /**
     * Method to insert data into City Table
     */
    public boolean insertCityTableData(String cityName, String deviation, String latMax, String latMin, String lngMax, String lngMin) {
        SQLiteDatabase db = this.getWritableDatabase();
        CityTableHelper helper = new CityTableHelper(db);
        return helper.insertData(cityName, deviation, latMax, latMin, lngMax, lngMin);
    }

    /**
     * Method to check whether a particular city is present in the database
     */
    public ArrayList<String> getCityData(String cityName) {
        SQLiteDatabase db = this.getWritableDatabase();
        CityTableHelper helper = new CityTableHelper(db);
        return helper.getData(cityName);
    }

    /**
     * Method to insert data into Intersection table
     */
    public boolean insertIntersectionTableData(JSONArray nodeArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        IntersectionTableHelper helper = new IntersectionTableHelper(db);
        return  helper.insertData(nodeArray);
    }

    /**
     * Method to get the cursor to the entire Intersections table
     */
    public Cursor getIntersectionsTableCursor() {
        SQLiteDatabase db = this.getWritableDatabase();
        IntersectionTableHelper helper = new IntersectionTableHelper(db);
        return helper.getCursor();
    }

    /**
     * Method to insert data into Paths Table
     */
    public boolean insertPathsTableData(JSONArray wayArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        PathsTableHelper helper = new PathsTableHelper(db);
        return helper.insertData(wayArray);
    }

    /**
     * Method to get a cursor to the entire paths table database
     */
    public Cursor getPathsTableCursor() {
        SQLiteDatabase db = this.getWritableDatabase();
        PathsTableHelper helper = new PathsTableHelper(db);
        return helper.getCursor();
    }

}
