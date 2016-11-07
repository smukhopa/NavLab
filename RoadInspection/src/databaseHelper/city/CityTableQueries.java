package databaseHelper.city;

import java.util.ArrayList;

import databaseHelper.Attributes;

public class CityTableQueries {
		
	protected static String getCity(String cityName) {
		return "SELECT * FROM " + Attributes.DB_NAME + "." + CityTableAttributes.TABLE_NAME + 
				" WHERE " + CityTableAttributes.CITY_NAME + " = " + "'" + cityName + "';";
	}
	
	protected static String insertCity(ArrayList<String> arr) {
		return "INSERT INTO " + Attributes.DB_NAME + "." + CityTableAttributes.TABLE_NAME + 
				" (" + CityTableAttributes.CITY_NAME + "," +
				CityTableAttributes.DEVIATION + "," +
				CityTableAttributes.LAT_MAX + "," +
				CityTableAttributes.LAT_MIN + "," +
				CityTableAttributes.LNG_MAX + "," + 
				CityTableAttributes.LNG_MIN + ") VALUES (" +
				"'" + arr.get(0) +"', " + 
				"'0', " +
				"'" + arr.get(1) + "', " +
				"'" + arr.get(2) + "', " +
				"'" + arr.get(3) + "', " +
				"'" + arr.get(4) + "');";
	}
	
	protected static String deleteCity(String cityName) {
		return "DELETE FROM " + Attributes.DB_NAME + "." + CityTableAttributes.TABLE_NAME + 
				" WHERE " + CityTableAttributes.CITY_NAME + " = " + "'" + cityName +  "';";
	}
}
