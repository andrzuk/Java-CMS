package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Excludes_Dao;

public class Excludes_Model {

	private Excludes_Dao exclude;
	private List<Excludes_Dao> excludes;

	public Excludes_Model() {
	}

	public Excludes_Model(Excludes_Dao exclude) {

		this.exclude = exclude;
	}

	public Excludes_Model(List<Excludes_Dao> excludes) {

		this.excludes = excludes;
	}

	public int save() throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "INSERT INTO excludes"
					+ " (id, visitor_ip, active) VALUES"
					+ " (NULL, ?, ?)";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, exclude.getVisitor_ip());
			preparedStatement.setBoolean(2, exclude.isActive());
			
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

	public int saveAll() throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {

			query = "DELETE FROM excludes";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			result += preparedStatement.executeUpdate();
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
		
		try {

			query = "INSERT INTO excludes"
					+ " (id, visitor_ip, active) VALUES"
					+ " (NULL, ?, ?)";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			for (int i = 0; i < excludes.size(); i++) {
				
				preparedStatement.setString(1, excludes.get(i).getVisitor_ip());
				preparedStatement.setInt(2, excludes.get(i).isActive() ? 1 : 0);
				
				result += preparedStatement.executeUpdate();
			}
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

	public List<Excludes_Dao> getAll() throws SQLException {

		List<Excludes_Dao> excludes = new ArrayList<Excludes_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT * FROM excludes ORDER BY id";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                exclude = new Excludes_Dao();
                
                exclude.setId(rs.getInt("id"));
                exclude.setVisitor_ip(rs.getString("visitor_ip"));
                exclude.setActive(rs.getBoolean("active"));
                excludes.add(exclude);
            }			
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
		
		return excludes;
	}

	public List<String> getActive() throws SQLException {

		List<String> excludes = new ArrayList<String>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT visitor_ip FROM excludes WHERE active = 1 ORDER BY id";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                excludes.add(rs.getString("visitor_ip"));
            }			
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
		
		return excludes;
	}
}
