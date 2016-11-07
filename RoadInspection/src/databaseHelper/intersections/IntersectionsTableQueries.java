package databaseHelper.intersections;

import java.util.ArrayList;

import databaseHelper.Attributes;

public class IntersectionsTableQueries {
	
	protected static String readIntersection(String id) {
		return "SELECT * FROM " + Attributes.DB_NAME + "." + IntersectionsTableAttributes.TABLE_NAME + 
				" WHERE " + IntersectionsTableAttributes.ID + " = " + "'" + id + "';";
	}
	
	protected static String insertIntersection(ArrayList<String> arr) {
		return "INSERT INTO " + Attributes.DB_NAME + "." + IntersectionsTableAttributes.TABLE_NAME + 
				" (" + IntersectionsTableAttributes.ID + "," +
				IntersectionsTableAttributes.LAT + "," +
				IntersectionsTableAttributes.LNG + ") VALUES (" +
				"'" + arr.get(0) +"', " + 
				"'" + arr.get(1) + "', " +
				"'" + arr.get(2) + "');";
	}
	
	protected static String deleteIntersection(String id) {
		return "DELETE FROM " + Attributes.DB_NAME + "." + IntersectionsTableAttributes.TABLE_NAME + 
				" WHERE " + IntersectionsTableAttributes.ID + " = " + "'" + id +  "';";
	}
	
}
