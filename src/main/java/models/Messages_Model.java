package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.Messages_Dao;
import utilities.Descriptions;
import utilities.Paginator;
import utilities.Sorting;

public class Messages_Model {

	private static final String TABLE = "messages";
	
	private Messages_Dao message;
	
	private String filter;

	public Messages_Model() {
	}

	public Messages_Model(Messages_Dao message) {

		this.message = message;
	}
	
	private Messages_Dao setRecord(ResultSet rs, Messages_Dao message) throws SQLException, ParseException {
		
        message.setId(rs.getInt("id"));
        message.setNick(rs.getString("nick"));
        message.setEmail(rs.getString("email"));
        message.setIp(rs.getString("ip"));
        message.setMessage(rs.getString("message"));
        message.setVisible(rs.getBoolean("visible"));
        message.setModified(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("modified")));
        
        return message;
	}
	
	public void setFilter(String filter) {
		
		this.filter = filter != null ? filter : "";
	}

	private String getCondition() throws SQLException {
		
        String result = "nick LIKE '%" + filter + "%'" +
                        " OR email LIKE '%" + filter + "%'" +
                        " OR ip LIKE '%" + filter + "%'" +
                        " OR message LIKE '%" + filter + "%'";

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

	public List<Messages_Dao> getSegment(Paginator paginator, Sorting sorting) throws SQLException, ParseException {

		List<Messages_Dao> messages = new ArrayList<Messages_Dao>();
		Descriptions description = new Descriptions();
		Config_Model config = new Config_Model();
		String shortContent = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		int starting_from = paginator.getRows_per_page() * paginator.getPage_index();
		String starting = new Integer(starting_from).toString();
		String showing = new Integer(paginator.getRows_per_page()).toString();

		try {
			
			query = "SELECT " + TABLE + ".*" +
					" FROM " + TABLE +
					" WHERE " + getCondition() + 
					" ORDER BY " + sorting.getSort_field() + " " + sorting.getSort_order() +
			        " LIMIT " + starting + ", " + showing;

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				message = setRecord(rs, new Messages_Dao());
				
				shortContent = description.removeTags(message.getMessage());
				message.setMessage(description.getIntroduce(shortContent, Integer.parseInt(config.getConfig("description_words_messages"))));
				
                messages.add(message);
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
		
		return messages;
	}

	public Messages_Dao getOne(int id) throws SQLException, ParseException {

		Messages_Dao message = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*" +
					" FROM " + TABLE +
					" WHERE " + TABLE + ".id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				message = setRecord(rs, new Messages_Dao());
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
		
		return message;
	}

	public int save() throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "INSERT INTO " + TABLE +
					" (id, nick, email, ip, message, visible, modified) VALUES" +
					" (NULL, ?, ?, ?, ?, ?, NOW())";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, message.getNick());
			preparedStatement.setString(2, message.getEmail());
			preparedStatement.setString(3, message.getIp());
			preparedStatement.setString(4, message.getMessage());
			preparedStatement.setString(5, message.isVisible() ? "1" : "0");
			
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
					" SET message = ?, visible = ?" +
					" WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, message.getMessage());
			preparedStatement.setString(2, message.isVisible() ? "1" : "0");
			preparedStatement.setInt(3, id);
			
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
