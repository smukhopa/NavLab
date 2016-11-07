package com.example.smukhopadhyay.database.intersectionTable;

/**
 * Created by smukhopadhyay on 10/3/16.
 */
public class IntersectionTableQueries {

    public static final String CREATE_TABLE_QUERY =
        "CREATE TABLE IF NOT EXISTS " + IntersectionTableAttributes.TABLE_NAME +
            "(" +
            IntersectionTableAttributes.ID + " TEXT PRIMARY KEY," +
            IntersectionTableAttributes.LAT + " TEXT," +
            IntersectionTableAttributes.LNG + " TEXT" +
            ")";

    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + IntersectionTableAttributes.TABLE_NAME;

    public static final String GET_ALL_DATA = "SELECT * FROM " + IntersectionTableAttributes.TABLE_NAME;

    public static String getLatLongQuery(String nodeID) {
        return "SELECT * FROM " + IntersectionTableAttributes.TABLE_NAME +
                " WHERE " + IntersectionTableAttributes.ID + " LIKE '" + nodeID + "'";
    }

}
