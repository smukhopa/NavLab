package databaseHelper.city;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CityTableHelper {
	
	private Connection connection;
	
	public CityTableHelper(Connection connection) {
		this.connection = connection;
	}
	
	/*
	 * Read all cities
	 */
	public ArrayList<ArrayList<String>> readData() {
		// TODO
		return null;
	}
	
	/*
	 * Read particular city
	 */
	public ArrayList<String> readData(String cityName) {
		ArrayList<String> res = new ArrayList<>();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery(CityTableQueries.getCity(cityName));
			
			while (set.next()) {
				String city_name = set.getString(CityTableAttributes.CITY_NAME);
				String latMax = set.getString(CityTableAttributes.LAT_MAX);
				String latMin = set.getString(CityTableAttributes.LAT_MIN);
				String lngMax = set.getString(CityTableAttributes.LNG_MAX);
				String lngMin = set.getString(CityTableAttributes.LNG_MIN);
				res.add(city_name);
				res.add(latMax);
				res.add(latMin);
				res.add(lngMax);
				res.add(lngMin);
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
			statement.executeUpdate(CityTableQueries.insertCity(arr));
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
	public boolean deleteData(String cityName) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(CityTableQueries.deleteCity(cityName));
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
		String cityName = arr.get(0);
		try {
			// First delete
			Statement statement = connection.createStatement();
			statement.executeUpdate(CityTableQueries.deleteCity(cityName));
			
			// Then insert
			statement.executeUpdate(CityTableQueries.insertCity(arr));
		} catch(SQLException e) {
			System.out.println("Connection terminated");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
}
