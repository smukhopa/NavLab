package databaseHelper.paths;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PathsTableHelper {
private Connection connection;
	
	public PathsTableHelper(Connection connection) {
		this.connection = connection;
	}
	
	/*
	 * Read all Paths
	 */
	public ArrayList<ArrayList<String>> readData() {
		// TODO
		return null;
	}
	
	/*
	 * Read particular path
	 */
	public ArrayList<String> readData(String str) {
		ArrayList<String> res = new ArrayList<>();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery(PathsTableQueries.getPath(str));
			
			while (set.next()) {
				String id = set.getString(PathsTableAttributes.ID);
				String name = set.getString(PathsTableAttributes.NAME);
				String startPoint = set.getString(PathsTableAttributes.START_POINT);
				String startPointLat = set.getString(PathsTableAttributes.START_POINT_LAT);
				String startPointLng = set.getString(PathsTableAttributes.START_POINT_LNG);
				String endPoint = set.getString(PathsTableAttributes.END_POINT);
				String endPointLat = set.getString(PathsTableAttributes.END_POINT_LAT);
				String endPointLng = set.getString(PathsTableAttributes.END_POINT_LNG);
				String length = set.getString(PathsTableAttributes.LENGTH);
				String start2end = set.getString(PathsTableAttributes.START_2_END);
				String end2Start = set.getString(PathsTableAttributes.END_2_START);
				String start2endTraversed = set.getString(PathsTableAttributes.START_2_END_TRAVERSED);
				String end2StartTraversed = set.getString(PathsTableAttributes.END_2_START_TRAVERSED);
				String calcWeight = set.getString(PathsTableAttributes.CALC_WEIGHT);
				
				res.add(id);
				res.add(name);
				res.add(startPoint);
				res.add(startPointLat);
				res.add(startPointLng);
				res.add(endPoint);
				res.add(endPointLat);
				res.add(endPointLng);
				res.add(length);
				res.add(start2end);
				res.add(end2Start);
				res.add(start2endTraversed);
				res.add(end2StartTraversed);
				res.add(calcWeight);
			}
		} catch(SQLException e) {
			System.out.println("Connection terminated");
			e.printStackTrace();
		}
		return res;
	}
	
	/*
	 * Write
	 */
	public boolean writeData(ArrayList<String> arr) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(PathsTableQueries.insertPath(arr));
		} catch(SQLException e) {
			System.out.println("Connection terminated");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*
	 * Delete
	 */
	public boolean deleteData(String id) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(PathsTableQueries.deletePath(id));
		} catch(SQLException e) {
			System.out.println("Connection terminated");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*
	 * Update
	 */
	public boolean updateData(ArrayList<String> arr) {
		String id = arr.get(0);
		try {
			// First delete
			Statement statement = connection.createStatement();
			statement.executeUpdate(PathsTableQueries.deletePath(id));
			
			// Then insert
			statement.executeUpdate(PathsTableQueries.insertPath(arr));
		} catch(SQLException e) {
			System.out.println("Connection terminated");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
