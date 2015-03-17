package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.Config_Dao;

import utilities.Paginator;
import utilities.Sorting;

public class Config_Model {

	private static final String TABLE = "configuration";
	
	private Config_Dao config;
	
	private String filter;

	public Config_Model() {
	}

	public Config_Model(Config_Dao config) {

		this.config = config;
	}
	
	private Config_Dao setRecord(ResultSet rs, Config_Dao config) throws SQLException, ParseException {
		
        config.setId(rs.getInt("id"));
        config.setKey_name(rs.getString("key_name"));
        config.setKey_value(rs.getString("key_value"));
        config.setMeaning(rs.getString("meaning"));
        config.setField_type(rs.getInt("field_type"));
        config.setModified(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("modified")));
        config.setActive(rs.getBoolean("active"));
        
        return config;
	}
	
	public void setFilter(String filter) {
		
		this.filter = filter != null ? filter : "";
	}

	private String getCondition() throws SQLException {
		
        String result = "key_name LIKE '%" + filter + "%'" +
                        " OR key_value LIKE '%" + filter + "%'" +
                        " OR meaning LIKE '%" + filter + "%'";

		return result;
	}

	public int getCount() throws SQLException {

		int counter = 0;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT COUNT(*) AS licznik FROM " + TABLE +
			        " WHERE " + getCondition();

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                counter = rs.getInt("licznik");
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
		
		return counter;
	}

	public List<Config_Dao> getSegment(Paginator paginator, Sorting sorting) throws SQLException, ParseException {

		List<Config_Dao> configs = new ArrayList<Config_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		int starting_from = paginator.getRows_per_page() * paginator.getPage_index();
		String starting = new Integer(starting_from).toString();
		String showing = new Integer(paginator.getRows_per_page()).toString();

		try {
			
			query = "SELECT * FROM " + TABLE +
					" WHERE " + getCondition() + 
					" ORDER BY " + sorting.getSort_field() + " " + sorting.getSort_order() +
			        " LIMIT " + starting + ", " + showing;

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                configs.add(setRecord(rs, new Config_Dao()));
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
		
		return configs;
	}

	public Config_Dao getOne(int id) throws SQLException, ParseException {

		Config_Dao config = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT * FROM " + TABLE + " WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				config = setRecord(rs, new Config_Dao());
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
		
		return config;
	}

	public String getConfig(String keyname) throws SQLException, ParseException {

		String config = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT * FROM " + TABLE + " WHERE key_name = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, keyname);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				config = rs.getString("key_value");
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
		
		return config;
	}

	public int save() throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "INSERT INTO " + TABLE +
					" (id, key_name, key_value, meaning, field_type, active, modified) VALUES" +
					" (NULL, ?, ?, ?, ?, ?, NOW())";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, config.getKey_name());
			preparedStatement.setString(2, config.getKey_value());
			preparedStatement.setString(3, config.getMeaning());
			preparedStatement.setInt(4, config.getField_type());
			preparedStatement.setString(5, config.isActive() ? "1" : "0");

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

	public int update(int id) throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "UPDATE " + TABLE +
					" SET key_name = ?, key_value = ?, meaning = ?, field_type = ?, active = ?, modified = NOW()" +
					" WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, config.getKey_name());
			preparedStatement.setString(2, config.getKey_value());
			preparedStatement.setString(3, config.getMeaning());
			preparedStatement.setInt(4, config.getField_type());
			preparedStatement.setString(5, config.isActive() ? "1" : "0");
			preparedStatement.setInt(6, id);
			
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

	public int delete(int id) throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "DELETE FROM " + TABLE + " WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
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
