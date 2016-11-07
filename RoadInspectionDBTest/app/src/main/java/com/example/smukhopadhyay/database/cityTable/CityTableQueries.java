package com.example.smukhopadhyay.database.cityTable;

/**
 * Created by smukhopadhyay on 10/3/16.
 */
public class CityTableQueries {

        public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + CityTableAttributes.TABLE_NAME +
                    "(" +
                    CityTableAttributes.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CityTableAttributes.CITY_NAME + " TEXT," +
                    CityTableAttributes.DEVIATION + " REAL," +
                    CityTableAttributes.LAT_MAX + " TEXT," +
                    CityTableAttributes.LAT_MIN + " TEXT," +
                    CityTableAttributes.LNG_MAX + " TEXT," +
                    CityTableAttributes.LNG_MIN + " TEXT" +
                    ");";


        public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + CityTableAttributes.TABLE_NAME;

    public static final String GET_ALL_DATA = "SELECT * FROM " + CityTableAttributes.TABLE_NAME;

    public static String getCityQuery(String cityName) {
        return "SELECT * FROM " + CityTableAttributes.TABLE_NAME +
                " WHERE " + CityTableAttributes.CITY_NAME + " LIKE '" + cityName + "'";
    }

}