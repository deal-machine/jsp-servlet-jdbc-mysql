package net.java.usermanagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {

	
	private static final String jdbcURL = "jdbc:mysql://localhost:3306/user_management";
	private static final String jdbcUsername = "root";
	private static final String jdbcPassword = "fpo-5384_E";

	public static Connection getConnection() {
		Connection connection = null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
            DriverManager.registerDriver(new com.mysql.jdbc.Driver()); 
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}catch(SQLException e){
			e.printStackTrace();throw new RuntimeException(e);
		}
		return connection;
	}
}
