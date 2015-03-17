package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tools {

	public int debugVariable(String key, String value) throws SQLException {

		int result = 0;
		String query;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "INSERT INTO debug"
					+ " (id, debug_key, debug_value, saved) VALUES"
					+ " (NULL, ?, ?, NOW())";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, key);
			preparedStatement.setString(2, value);
			
			result = preparedStatement.executeUpdate();
		} 
		catch (SQLException e) {

			System.out.println(e.getMessage());
		} 
		finally {

			if (preparedStatement != null) {

				preparedStatement.close();
			}
			if (db.Connect.getDbConnection() != null) {

				db.Connect.getDbConnection().close();
			}
		}
		
		return result;
	}
}
