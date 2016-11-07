package databaseHelper.intersections;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class IntersectionsTableHelper {
	
	private Connection connection;
	
	public IntersectionsTableHelper(Connection connection) {
		this.connection = connection;
	}
	
	/*
	 * Read all intersections
	 */
	public ArrayList<ArrayList<String>> readData() {
		// TODO
		return null;
	}
	
	/*
	 * Read a particular intersection
	 */
	public ArrayList<String> readData(String str) {
		ArrayList<String> res = new ArrayList<>();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery(IntersectionsTableQueries.readIntersection(str));
			
			while (set.next()) {
				String id = set.getString(IntersectionsTableAttributes.ID);
				String lat = set.getString(IntersectionsTableAttributes.LAT);
				String lng = set.getString(IntersectionsTableAttributes.LNG);
				res.add(id);
				res.add(lat);
				res.add(lng);
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
			statement.executeUpdate(IntersectionsTableQueries.insertIntersection(arr));
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
			statement.executeUpdate(IntersectionsTableQueries.deleteIntersection(id));
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
			statement.executeUpdate(IntersectionsTableQueries.deleteIntersection(id));
			
			// Then insert
			statement.executeUpdate(IntersectionsTableQueries.insertIntersection(arr));
		} catch(SQLException e) {
			System.out.println("Connection terminated");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
