package databaseHelper.paths;

import java.util.ArrayList;

import databaseHelper.Attributes;

public class PathsTableQueries {

	protected static String getPath(String id) {
		return "SELECT * FROM " + Attributes.DB_NAME + "." + PathsTableAttributes.TABLE_NAME + 
				" WHERE " + PathsTableAttributes.ID + " = " + "'" + id + "';";
	}
	
	protected static String insertPath(ArrayList<String> arr) {
		return "INSERT INTO " + Attributes.DB_NAME + "." + PathsTableAttributes.TABLE_NAME + 
				" (" + PathsTableAttributes.ID + "," +
				PathsTableAttributes.NAME + "," +
				PathsTableAttributes.START_POINT + "," +
				PathsTableAttributes.START_POINT_LAT + "," +
				PathsTableAttributes.START_POINT_LNG + "," + 
				PathsTableAttributes.END_POINT + "," +
				PathsTableAttributes.END_POINT_LAT + "," +
				PathsTableAttributes.END_POINT_LNG + "," + 
				PathsTableAttributes.LENGTH + ") VALUES (" +
				"'" + arr.get(0) + "', " + 
				"\"" + arr.get(1) + "\", " +
				"'" + arr.get(2) + "', " +
				"'" + arr.get(3) + "', " +
				"'" + arr.get(4) + "', " +
				"'" + arr.get(5) + "', " +
				"'" + arr.get(6) + "', " +
				"'" + arr.get(7) + "', " +
				"'" + arr.get(8) + "');";
	}
	
	protected static String deletePath(String id) {
		return "DELETE FROM " + Attributes.DB_NAME + "." + PathsTableAttributes.TABLE_NAME + 
				" WHERE " + PathsTableAttributes.ID + " = " + "'" + id +  "';";
	}
}
