package databaseHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import databaseHelper.city.CityTableHelper;
import databaseHelper.intersections.IntersectionsTableHelper;
import databaseHelper.paths.PathsTableHelper;

public class DatabaseHelper {

	private Connection connection;

	public DatabaseHelper() {
		this.connection = connectToDB();
	}

	/*
	 * Connect to database and return the connection variable
	 */
	private Connection connectToDB() {
		try {
			DBConnection connection = new DBConnection();
			Connection con = connection.connect();
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * City Table helper methods
	 */
	public ArrayList<ArrayList<String>> readCityTable() {
		CityTableHelper helper = new CityTableHelper(connection);
		return helper.readData();
	}

	public ArrayList<String> readCityTable(String cityName) {
		CityTableHelper helper = new CityTableHelper(connection);
		return helper.readData(cityName);
	}

	public boolean writeCityTable(ArrayList<String> arr) {
		CityTableHelper helper = new CityTableHelper(connection);
		return helper.writeData(arr);
	}

	public boolean updateCityTable(ArrayList<String> arr) {
		CityTableHelper helper = new CityTableHelper(connection);
		return helper.updateData(arr);
	}

	public boolean deleterCityTable(String id) {
		CityTableHelper helper = new CityTableHelper(connection);
		return helper.deleteData(id);
	}

	/*
	 * Intersection Table helper methods
	 */
	public ArrayList<ArrayList<String>> readIntersectionTable() {
		IntersectionsTableHelper helper = new IntersectionsTableHelper(connection);
		return helper.readData();
	}

	public ArrayList<String> readIntersectionTable(String id) {
		IntersectionsTableHelper helper = new IntersectionsTableHelper(connection);
		return helper.readData(id);
	}

	public boolean writeIntersectionTable(ArrayList<String> arr) {
		IntersectionsTableHelper helper = new IntersectionsTableHelper(connection);
		return helper.writeData(arr);
	}

	public boolean writeIntersectionTable(JSONObject json) {
		
		try {
			
			JSONObject osmObject = json.getJSONObject("osm");
			JSONArray nodeArray = osmObject.getJSONArray("node");
			
			for (int i = 0; i < nodeArray.length(); i++) {
				JSONObject nodeObject = nodeArray.getJSONObject(i);
				String id = nodeObject.optString("id");
				String lat = nodeObject.optString("lat");
				String lng = nodeObject.optString("lon");
				ArrayList<String> arr = new ArrayList<>();
				arr.add(id);
				arr.add(lat);
				arr.add(lng);
				boolean res = writeIntersectionTable(arr);
				if (res == false) {
					return false;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateIntersectionTable(ArrayList<String> arr) {
		IntersectionsTableHelper helper = new IntersectionsTableHelper(connection);
		return helper.updateData(arr);
	}

	public boolean deleterIntersectionTable(String id) {
		IntersectionsTableHelper helper = new IntersectionsTableHelper(connection);
		return helper.deleteData(id);
	}

	/*
	 * Path Table helper methods
	 */
	public ArrayList<ArrayList<String>> readPathTable() {
		PathsTableHelper helper = new PathsTableHelper(connection);
		return helper.readData();
	}

	public ArrayList<String> readPathTable(String id) {
		PathsTableHelper helper = new PathsTableHelper(connection);
		return helper.readData(id);
	}

	public boolean writePathTable(ArrayList<String> arr) {
		PathsTableHelper helper = new PathsTableHelper(connection);
		return helper.writeData(arr);
	}

	public boolean writePathTable(JSONObject json) {
		
		try {
			JSONObject osmObject = json.getJSONObject("osm");
			JSONArray wayArray = osmObject.getJSONArray("way");
			int count = -1;
			for (int i = 0; i < wayArray.length(); i++) {
				count++;
				JSONObject wayObject = wayArray.getJSONObject(i);
				
				// Only if there's tag, then the road is worth looking into
				if (wayObject.optJSONArray("tag") != null) {
					
					String id = wayObject.optString("id");
					
					// Go through the tag array and check whether the highway key exists
					JSONArray tagArray = wayObject.optJSONArray("tag");
					
					boolean isHighway = false;
					String name = "";
					
					for (int j = 0; j < tagArray.length(); j++) {
						JSONObject tagObject = tagArray.optJSONObject(j);
						String key = tagObject.optString("k");
						if (key.equals("highway")) {
							isHighway = true;
						} else if (key.equals("name")) {
							name = tagObject.optString("v");
						}
					}
					
					if (isHighway) {
						// Get all the paths and store in arraylist
						JSONArray ndArray = wayObject.getJSONArray("nd");
						ArrayList<ArrayList<String>> path = new ArrayList<ArrayList<String>>();
						for (int j = 0; j < ndArray.length(); j++) {
							JSONObject ndObject = ndArray.getJSONObject(j);
							String ref = ndObject.optString("ref");
							path.add(readIntersectionTable(ref));
						}
						
						for (int j = 0; j < path.size() - 1; j++) {
							ArrayList<String> start = path.get(j);
							ArrayList<String> finish = path.get(j + 1);
							
							if (start.size() == 3 && finish.size() == 3) {
								String startPoint = start.get(0);
								String startLat = start.get(1);
								String startLng = start.get(2);
								
								String finishPoint = finish.get(0);
								String finishLat = finish.get(1);
								String finishLng = finish.get(2);
								
								ArrayList<String> arr = new ArrayList<>();
								String actualID = id + Integer.toString(j) + Integer.toString(j + 1);
								System.out.println(count + ". " + actualID);
								arr.add(actualID);
								arr.add(name);
								arr.add(startPoint);
								arr.add(startLat);
								arr.add(startLng);
								arr.add(finishPoint);
								arr.add(finishLat);
								arr.add(finishLng);
								// TODO
								// Get the len using either google maps or calculation
								arr.add("1");
								writePathTable(arr);
							}
						}
					}
				}
			} 
			
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		
		return false;
	}

	public boolean updatePathTable(ArrayList<String> arr) {
		PathsTableHelper helper = new PathsTableHelper(connection);
		return helper.updateData(arr);
	}

	public boolean deletePathTable(String id) {
		PathsTableHelper helper = new PathsTableHelper(connection);
		return helper.deleteData(id);
	}

}
