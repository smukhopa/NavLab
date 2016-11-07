package com.example.smukhopadhyay.database.pathsTable;

/**
 * Created by smukhopadhyay on 10/3/16.
 */
public class PathTableAttributes {

    // Name of the table
    protected static final String TABLE_NAME = "ROAD_SEGMENTS";

    // Names of all the columns
    protected static final String ID = "ID";
    protected static final String NAME = "NAME";

    protected static final String START_POINT = "START_POINT";
    protected static final String START_POINT_LAT = "START_POINT_LAT";
    protected static final String START_POINT_LNG = "START_POINT_LNG";

    protected static final String END_POINT = "END_POINT";
    protected static final String END_POINT_LAT = "END_POINT_LAT";
    protected static final String END_POINT_LNG = "END_POINT_LNG";

    protected static final String LENGTH = "LENGTH";

    protected static final String START_2_END = "START_2_END";
    protected static final String END_2_START = "END_2_START";

    protected static final String START_2_END_TRAVERSED = "START_2_END_TRAVERSED";
    protected static final String END_2_START_TRAVERSED = "END_2_START_TRAVERSED";

    protected static final String CALC_WEIGHT = "CALC_WEIGHT";

    // Name of logging variable
    public static final String LOG = "PATHS_TABLE_LOG";
}
