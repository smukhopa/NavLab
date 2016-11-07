package userInterface;

import java.util.ArrayList;

import org.json.JSONObject;

import databaseHelper.DatabaseHelper;
import mapsAPI.MapsAPIHelper;

public class UIHelper {
	
	public static void start() {
		ParseUserInput parse = new ParseUserInput();
		parse.getUserInput();
	}
	
	protected static void callMapsAPI(ArrayList<String> arr) {
		double maxLat = Double.parseDouble(arr.get(2));
		double minLat = Double.parseDouble(arr.get(3));
		double maxLng = Double.parseDouble(arr.get(4));
		double minLng = Double.parseDouble(arr.get(5));
		MapsAPIHelper helper = new MapsAPIHelper(maxLat, minLat, maxLng, minLng);
		JSONObject json = helper.connect();
		callDatabaseHelper(json);
	}
	
	protected static void callDatabaseHelper(JSONObject json) {
		DatabaseHelper dbHelper = new DatabaseHelper();
		dbHelper.writeIntersectionTable(json);
		dbHelper.writePathTable(json);
	}
	
	protected static void deleteOldCity() {
		DatabaseHelper dbHelper = new DatabaseHelper();
		
	}
}
