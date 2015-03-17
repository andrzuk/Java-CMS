package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

	public static Connection getDbConnection() {
		 
		Connection dbConnection = null;
 
		String APP_NAME = System.getenv("OPENSHIFT_APP_NAME");
		String DB_HOST =  System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		String DB_PORT =  System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		String DB_USER =  System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
		String DB_PASS =  System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");

		String DB_CONN = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + APP_NAME + "?characterEncoding=UTF-8";

		try {
 
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) {
 
			System.out.println(e.getMessage());
		}
 
		try {
 
			dbConnection = DriverManager.getConnection(DB_CONN, DB_USER, DB_PASS);
		} 
		catch (SQLException e) {
 
			System.out.println(e.getMessage());
		}
		
		return dbConnection;
	}	
}
