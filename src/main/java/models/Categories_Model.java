package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.Categories_Dao;

import utilities.Paginator;
import utilities.Sorting;

public class Categories_Model {

	private static final String TABLE = "categories";
	
	private Categories_Dao category;
	
	private String filter;

	public Categories_Model() {
	}

	public Categories_Model(Categories_Dao category) {

		this.category = category;
	}
	
	private Categories_Dao setRecord(ResultSet rs, Categories_Dao category) throws SQLException, ParseException {
		
        category.setId(rs.getInt("id"));
        category.setParent_id(rs.getInt("parent_id"));
        category.setItem_order(rs.getInt("item_order"));
        category.setCaption(rs.getString("caption"));
        category.setLink(rs.getString("link"));
        category.setVisible(rs.getBoolean("visible"));
        category.setAuthor_id(rs.getInt("author_id"));
        category.setLogin(rs.getString("login"));
        category.setModified(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("modified")));
        
        return category;
	}
	
	public void setFilter(String filter) {
		
		this.filter = filter != null ? filter : "";
	}

	private String getCondition() throws SQLException {
		
        String result = "caption LIKE '%" + filter + "%'" +
                        " OR link LIKE '%" + filter + "%'";

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

	public List<Categories_Dao> getSegment(Paginator paginator, Sorting sorting) throws SQLException, ParseException {

		List<Categories_Dao> categories = new ArrayList<Categories_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		int starting_from = paginator.getRows_per_page() * paginator.getPage_index();
		String starting = new Integer(starting_from).toString();
		String showing = new Integer(paginator.getRows_per_page()).toString();

		try {
			
			query = "SELECT " + TABLE + ".*, users.login" +
					" FROM " + TABLE +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" WHERE " + getCondition() + 
					" ORDER BY " + sorting.getSort_field() + " " + sorting.getSort_order() +
			        " LIMIT " + starting + ", " + showing;

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                categories.add(setRecord(rs, new Categories_Dao()));
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
		
		return categories;
	}

	public List<Categories_Dao> getAll() throws SQLException, ParseException {

		List<Categories_Dao> categories = new ArrayList<Categories_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, users.login" +
					" FROM " + TABLE +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" ORDER BY id";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                categories.add(setRecord(rs, new Categories_Dao()));
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
		
		return categories;
	}

	public List<Categories_Dao> getActives() throws SQLException, ParseException {

		List<Categories_Dao> categories = new ArrayList<Categories_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, users.login" +
					" FROM " + TABLE +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" WHERE visible = 1" + 
					" ORDER BY item_order";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                categories.add(setRecord(rs, new Categories_Dao()));
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
		
		return categories;
	}

	public Categories_Dao getOne(int id) throws SQLException, ParseException {

		Categories_Dao category = null;
		
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
				
				category = setRecord(rs, new Categories_Dao());
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
		
		return category;
	}

	public Categories_Dao getActive(int id) throws SQLException, ParseException {

		Categories_Dao category = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, users.login" + 
					" FROM " + TABLE +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" WHERE " + TABLE + ".id = ? AND " + TABLE + ".visible = 1";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				category = setRecord(rs, new Categories_Dao());
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
		
		return category;
	}

	public Categories_Dao getLast() throws SQLException, ParseException {

		Categories_Dao category = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, users.login" + 
					" FROM " + TABLE +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" ORDER BY id DESC LIMIT 1";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				category = setRecord(rs, new Categories_Dao());
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
		
		return category;
	}

	public int getFirstId() throws SQLException {

		int result = 0;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT id" +  
					" FROM " + TABLE +
					" WHERE visible = 1" +
					" ORDER BY item_order ASC LIMIT 1";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				result = rs.getInt("id");
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

	public int save() throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "INSERT INTO " + TABLE +
					" (id, parent_id, item_order, caption, link, visible, author_id, modified) VALUES" +
					" (NULL, ?, ?, ?, ?, ?, ?, NOW())";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, category.getParent_id());
			preparedStatement.setInt(2, category.getItem_order());
			preparedStatement.setString(3, category.getCaption());
			preparedStatement.setString(4, category.getLink());
			preparedStatement.setString(5, category.isVisible() ? "1" : "0");
			preparedStatement.setInt(6, category.getAuthor_id());
			
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
					" SET parent_id = ?, item_order = ?, caption = ?, link = ?, visible = ?, author_id = ?, modified = NOW()" +
					" WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, category.getParent_id());
			preparedStatement.setInt(2, category.getItem_order());
			preparedStatement.setString(3, category.getCaption());
			preparedStatement.setString(4, category.getLink());
			preparedStatement.setString(5, category.isVisible() ? "1" : "0");
			preparedStatement.setInt(6, category.getAuthor_id());
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
	
	public int moveUp(Categories_Dao category) throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "UPDATE " + TABLE +
					" SET item_order = ?, author_id = ?, modified = NOW()" +
					" WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			int new_item_order = category.getItem_order() > 0 ? category.getItem_order() - 1 : 0;
			
			preparedStatement.setInt(1, new_item_order);
			preparedStatement.setInt(2, category.getAuthor_id());
			preparedStatement.setInt(3, category.getId());
			
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
	
	public int moveDown(Categories_Dao category) throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "UPDATE " + TABLE +
					" SET item_order = ?, author_id = ?, modified = NOW()" +
					" WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			int new_item_order = category.getItem_order() + 1;
			
			preparedStatement.setInt(1, new_item_order);
			preparedStatement.setInt(2, category.getAuthor_id());
			preparedStatement.setInt(3, category.getId());
			
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
