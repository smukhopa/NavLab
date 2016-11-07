package com.example.smukhopadhyay.database.pathsTable;

/**
 * Created by smukhopadhyay on 10/3/16.
 */
public class PathTableQueries {

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + PathTableAttributes.TABLE_NAME +
                    " (" +
                    PathTableAttributes.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    PathTableAttributes.NAME + " TEXT," +
                    PathTableAttributes.START_POINT + " INTEGER," +
                    PathTableAttributes.START_POINT_LAT + " TEXT," +
                    PathTableAttributes.START_POINT_LNG + " TEXT," +
                    PathTableAttributes.END_POINT + " INTEGER," +
                    PathTableAttributes.END_POINT_LAT + " TEXT," +
                    PathTableAttributes.END_POINT_LNG + " TEXT," +
                    PathTableAttributes.LENGTH + " REAL," +
                    PathTableAttributes.START_2_END + " TEXT," +
                    PathTableAttributes.END_2_START + " TEXT," +
                    PathTableAttributes.START_2_END_TRAVERSED + " TEXT," +
                    PathTableAttributes.END_2_START_TRAVERSED + " TEXT," +
                    PathTableAttributes.CALC_WEIGHT + " REAL" +
                    ")";

    public static final String DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS " + PathTableAttributes.TABLE_NAME;

    public static final String GET_ALL_DATA =
            "SELECT * FROM " + PathTableAttributes.TABLE_NAME;
}
