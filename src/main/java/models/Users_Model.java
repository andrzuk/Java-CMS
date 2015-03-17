package models;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.Users_Dao;

import utilities.Paginator;
import utilities.Sorting;

public class Users_Model {

	private static final String TABLE = "users";
	
	private Users_Dao user;
	
	private String filter;

	public Users_Model() {
	}

	public Users_Model(Users_Dao user) {

		this.user = user;
	}
	
	private Users_Dao setRecord(ResultSet rs, Users_Dao user) throws SQLException, ParseException {
		
        user.setId(rs.getInt("id"));
        user.setFirst_name(rs.getString("first_name"));
        user.setLast_name(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setStatus(rs.getInt("status"));
        user.setPassword(rs.getString("password"));
        user.setModified(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("modified")));
        user.setActive(rs.getBoolean("active"));
        
        return user;
	}
	
	public void setFilter(String filter) {
		
		this.filter = filter != null ? filter : "";
	}

	private String getCondition() throws SQLException {
		
        String result = "first_name LIKE '%" + filter + "%'" +
                        " OR last_name LIKE '%" + filter + "%'" +
                        " OR login LIKE '%" + filter + "%'" +
                        " OR email LIKE '%" + filter + "%'";

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

	public List<Users_Dao> getSegment(Paginator paginator, Sorting sorting) throws SQLException, ParseException {

		List<Users_Dao> users = new ArrayList<Users_Dao>();
		
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
				
                users.add(setRecord(rs, new Users_Dao()));
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
		
		return users;
	}

	public Users_Dao getOne(int id) throws SQLException, ParseException {

		Users_Dao user = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT * FROM " + TABLE + " WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				user = setRecord(rs, new Users_Dao());
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
		
		return user;
	}

	public Users_Dao getLast() throws SQLException, ParseException {

		Users_Dao user = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT * FROM " + TABLE + " ORDER BY id DESC LIMIT 1";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				user = setRecord(rs, new Users_Dao());
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
		
		return user;
	}

	public int save() throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "INSERT INTO " + TABLE +
					" (id, login, first_name, last_name, email, status, registered, logged_in, modified, logged_out, active) VALUES" +
					" (NULL, ?, ?, ?, ?, ?, NOW(), NOW(), NOW(), NOW(), ?)";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getFirst_name());
			preparedStatement.setString(3, user.getLast_name());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setInt(5, user.getStatus());
			preparedStatement.setString(6, user.isActive() ? "1" : "0");

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
					" SET first_name = ?, last_name = ?, login = ?, email = ?, status = ?, modified = NOW(), active = ?" +
					" WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, user.getFirst_name());
			preparedStatement.setString(2, user.getLast_name());
			preparedStatement.setString(3, user.getLogin());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setInt(5, user.getStatus());			
			preparedStatement.setString(6, user.isActive() ? "1" : "0");
			preparedStatement.setInt(7, id);
			
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

	public int setPassword(int id) throws SQLException, NoSuchAlgorithmException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "UPDATE " + TABLE +
					" SET password = ?, modified = NOW()" +
					" WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, user.getEncodedPassword());
			preparedStatement.setInt(2, id);

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
	
	public Users_Dao authorize(Users_Dao user) throws SQLException, ParseException, NoSuchAlgorithmException {

		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT * FROM " + TABLE + 
					" WHERE (login = ? OR email = ?) AND password = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getLogin());
			preparedStatement.setString(3, user.getEncodedPassword());
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				user = setRecord(rs, new Users_Dao());
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
		
		return user;
	}
}
