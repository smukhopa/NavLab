package databaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	protected DBConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		// Registering the driver
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	}
	
	protected Connection connect() throws SQLException {
		
		// Establishing connection
		Connection connection = DriverManager.getConnection(Attributes.HOST, Attributes.USERNAME, Attributes.PASSWORD);
		return connection;
		
	}
	
}
