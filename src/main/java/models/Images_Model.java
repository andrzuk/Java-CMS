package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.Images_Dao;

import utilities.Paginator;
import utilities.Sorting;

public class Images_Model {

	private static final String TABLE = "images";
	
	private Images_Dao image;
	
	private String filter;

	public Images_Model() {
	}

	public Images_Model(Images_Dao image) {

		this.image = image;
	}
	
	private Images_Dao setRecord(ResultSet rs, Images_Dao image) throws SQLException, ParseException {
		
        image.setId(rs.getInt("id"));
        image.setFile_name(rs.getString("file_name"));
        image.setFile_size(rs.getLong("file_size"));
        image.setWidth(rs.getInt("width"));
        image.setHeight(rs.getInt("height"));
        image.setAuthor_id(rs.getInt("author_id"));
        image.setLogin(rs.getString("login"));
        image.setModified(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("modified")));
        
        return image;
	}
	
	public void setFilter(String filter) {
		
		this.filter = filter != null ? filter : "";
	}

	private String getCondition() throws SQLException {
		
        String result = "file_name LIKE '%" + filter + "%'";

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

	public List<Images_Dao> getSegment(Paginator paginator, Sorting sorting) throws SQLException, ParseException {

		List<Images_Dao> images = new ArrayList<Images_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		int starting_from = paginator.getRows_per_page() * paginator.getPage_index();
		String starting = new Integer(starting_from).toString();
		String showing = new Integer(paginator.getRows_per_page()).toString();

		try {
			
			query = "SELECT " + TABLE + ".*, NULL AS preview, users.login" +
					" FROM " + TABLE +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" WHERE " + getCondition() + 
					" ORDER BY " + sorting.getSort_field() + " " + sorting.getSort_order() +
			        " LIMIT " + starting + ", " + showing;

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                images.add(setRecord(rs, new Images_Dao()));
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
		
		return images;
	}

	public Images_Dao getOne(int id) throws SQLException, ParseException {

		Images_Dao image = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, users.login" + 
					" FROM " + TABLE +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" WHERE " + TABLE + ".id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				image = setRecord(rs, new Images_Dao());
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
		
		return image;
	}

	public int save() throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "INSERT INTO " + TABLE +
					" (id, file_name, file_size, width, height, author_id, modified) VALUES" +
					" (NULL, ?, ?, ?, ?, ?, NOW())";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, image.getFile_name());
			preparedStatement.setLong(2,  image.getFile_size());
			preparedStatement.setLong(3,  image.getWidth());
			preparedStatement.setLong(4,  image.getHeight());
			preparedStatement.setInt(5, image.getAuthor_id());

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
