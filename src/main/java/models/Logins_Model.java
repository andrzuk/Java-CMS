package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import utilities.Paginator;
import utilities.Sorting;
import dao.Logins_Dao;
import dao.Users_Dao;

public class Logins_Model {

	private Logins_Dao login;
	
	private String filter;
	
	public Logins_Model() {		
	}

	public Logins_Model(Logins_Dao login) {

		this.login = login;
	}
	
	public void setFilter(String filter) {
		
		this.filter = filter != null ? filter : "";
	}

	private String getCondition() throws SQLException {
		
		Excludes_Model modelObject = new Excludes_Model();
		
		List<String> excludes = modelObject.getActive();

		StringBuilder sb = new StringBuilder();
		
		sb.append("'NULL', ");
		
		for (String each: excludes) {

			sb.append("'");
			sb.append(each);
			sb.append("', ");
		}
		
        String result = "user_ip NOT IN (" + sb.toString().replaceAll(", $", "") + ")" +
                        " AND (user_ip LIKE '%" + filter + "%'" +
        		        " OR agent LIKE '%" + filter + "%'" +
        		        " OR login LIKE '%" + filter + "%'" +
        		        " OR password LIKE '%" + filter + "%')";

		return result;
	}

	public int save(Users_Dao user) throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "INSERT INTO logins"
					+ " (id, agent, user_ip, user_id, login, password, login_time) VALUES"
					+ " (NULL, ?, ?, ?, ?, ?, NOW())";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, login.getAgent());
			preparedStatement.setString(2, login.getUser_ip());
			preparedStatement.setInt(3, user != null ? user.getId() : 0);
			preparedStatement.setString(4, login.getLogin());
			preparedStatement.setString(5, login.getPassword());
			
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

	public List<Logins_Dao> getSegment(Paginator paginator, Sorting sorting) throws SQLException, ParseException {

		List<Logins_Dao> logins = new ArrayList<Logins_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;
		
		int starting_from = paginator.getRows_per_page() * paginator.getPage_index();
		String starting = new Integer(starting_from).toString();
		String showing = new Integer(paginator.getRows_per_page()).toString();

		try {
			
			query = "SELECT * FROM logins" +
			        " WHERE " + getCondition() + 
					" ORDER BY " + sorting.getSort_field() + " " + sorting.getSort_order() +
			        " LIMIT " + starting + ", " + showing;

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				login = new Logins_Dao();
                
                login.setId(rs.getInt("id"));
                login.setAgent(rs.getString("agent"));
                login.setUser_ip(rs.getString("user_ip"));
                login.setUser_id(rs.getInt("user_id"));
                login.setLogin(rs.getString("login"));
                login.setPassword(rs.getString("password"));
                login.setLogin_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("login_time")));
                
                logins.add(login);
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
		
		return logins;
	}
	
	public int getCount() throws SQLException {

		int counter = 0;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT COUNT(*) AS licznik FROM logins" +
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
}
