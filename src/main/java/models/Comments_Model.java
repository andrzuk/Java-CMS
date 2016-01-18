package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.Comments_Dao;
import dao.Counts_Dao;
import utilities.Descriptions;
import utilities.Paginator;
import utilities.Sorting;

public class Comments_Model {

	private static final String TABLE = "comments";
	
	private Comments_Dao comment;
	
	private String filter;

	public Comments_Model() {
	}

	public Comments_Model(Comments_Dao comment) {

		this.comment = comment;
	}
	
	private Comments_Dao setRecord(ResultSet rs, Comments_Dao comment) throws SQLException, ParseException {
		
        comment.setId(rs.getInt("id"));
        comment.setPage_id(rs.getInt("page_id"));
        comment.setTitle(rs.getString("title"));
        comment.setNick(rs.getString("nick"));
        comment.setEmail(rs.getString("email"));
        comment.setIp(rs.getString("ip"));
        comment.setComment(rs.getString("comment"));
        comment.setVisible(rs.getBoolean("visible"));
        comment.setModified(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("modified")));
        
        return comment;
	}
	
	public void setFilter(String filter) {
		
		this.filter = filter != null ? filter : "";
	}

	private String getCondition() throws SQLException {
		
        String result = "nick LIKE '%" + filter + "%'" +
                        " OR email LIKE '%" + filter + "%'" +
                        " OR ip LIKE '%" + filter + "%'" +
                        " OR comment LIKE '%" + filter + "%'";

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

	public List<Comments_Dao> getSegment(Paginator paginator, Sorting sorting) throws SQLException, ParseException {

		List<Comments_Dao> comments = new ArrayList<Comments_Dao>();
		Descriptions description = new Descriptions();
		Config_Model config = new Config_Model();
		String shortContent = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		int starting_from = paginator.getRows_per_page() * paginator.getPage_index();
		String starting = new Integer(starting_from).toString();
		String showing = new Integer(paginator.getRows_per_page()).toString();

		try {
			
			query = "SELECT " + TABLE + ".*, pages.title" +
					" FROM " + TABLE +
					" INNER JOIN pages ON pages.id = " + TABLE + ".page_id" +
					" WHERE " + getCondition() + 
					" ORDER BY " + sorting.getSort_field() + " " + sorting.getSort_order() +
			        " LIMIT " + starting + ", " + showing;

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				comment = setRecord(rs, new Comments_Dao());
				
				shortContent = description.removeTags(comment.getComment());
				comment.setComment(description.getIntroduce(shortContent, Integer.parseInt(config.getConfig("description_words_comments"))));
				
                comments.add(comment);
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
		
		return comments;
	}

	public Comments_Dao getOne(int id) throws SQLException, ParseException {

		Comments_Dao comment = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, pages.title" +
					" FROM " + TABLE +
					" INNER JOIN pages ON pages.id = " + TABLE + ".page_id" +
					" WHERE " + TABLE + ".id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				comment = setRecord(rs, new Comments_Dao());
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
		
		return comment;
	}

	public List<Comments_Dao> getActives(int id) throws SQLException, ParseException {

		List<Comments_Dao> comments = new ArrayList<Comments_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, pages.title" +
					" FROM " + TABLE +
					" INNER JOIN pages ON pages.id = " + TABLE + ".page_id" +
					" WHERE " + TABLE + ".page_id = ? AND " + TABLE + ".visible = 1" + 
					" ORDER BY " + TABLE + ".id";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                comments.add(setRecord(rs, new Comments_Dao()));
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
		
		return comments;
	}

	public List<Counts_Dao> getCategoryCommentCounts(int id) throws SQLException, ParseException {

		List<Counts_Dao> counts = new ArrayList<Counts_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT page_id, COUNT(*) AS licznik" +
					" FROM " + TABLE + 
					" INNER JOIN pages ON pages.id = " + TABLE + ".page_id" +
					" INNER JOIN categories ON categories.id = pages.category_id" +
					" WHERE category_id = ? AND " + TABLE + ".visible = 1" + 
					" GROUP BY page_id";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				Counts_Dao count = new Counts_Dao();
				
				count.setPage_id(rs.getInt("page_id"));
				count.setComments_count(rs.getInt("licznik"));
				
				counts.add(count);
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
		
		return counts;
	}

	public List<Counts_Dao> getCategoryViewsCounts(int id) throws SQLException, ParseException {

		List<Counts_Dao> counts = new ArrayList<Counts_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT page_id, counter" +
					" FROM views" + 
					" INNER JOIN pages ON pages.id = views.page_id" +
					" INNER JOIN categories ON categories.id = pages.category_id" +
					" WHERE category_id = ?" + 
					" GROUP BY page_id";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				Counts_Dao count = new Counts_Dao();
				
				count.setPage_id(rs.getInt("page_id"));
				count.setComments_count(rs.getInt("counter"));
				
				counts.add(count);
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
		
		return counts;
	}

	public List<Counts_Dao> getFoundCommentCounts(String search) throws SQLException, ParseException {

		List<Counts_Dao> counts = new ArrayList<Counts_Dao>();
		String search_mask = search.replaceAll(" ", "%");
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT page_id, COUNT(*) AS licznik" +
					" FROM " + TABLE + 
					" INNER JOIN pages ON pages.id = " + TABLE + ".page_id" +
					" INNER JOIN categories ON categories.id = pages.category_id" +
					" WHERE (contents LIKE ? OR title LIKE ? OR description LIKE ? OR caption LIKE ?) AND " + TABLE + ".visible = 1" + 
					" GROUP BY page_id";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, "%" + search_mask + "%");
			preparedStatement.setString(2, "%" + search_mask + "%");
			preparedStatement.setString(3, "%" + search_mask + "%");
			preparedStatement.setString(4, "%" + search_mask + "%");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				Counts_Dao count = new Counts_Dao();
				
				count.setPage_id(rs.getInt("page_id"));
				count.setComments_count(rs.getInt("licznik"));
				
				counts.add(count);
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
		
		return counts;
	}

	public Counts_Dao getArticleCommentCount(int id) throws SQLException, ParseException {

		Counts_Dao count = new Counts_Dao();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT COUNT(*) AS licznik" +
					" FROM " + TABLE + 
					" INNER JOIN pages ON pages.id = " + TABLE + ".page_id" +
					" INNER JOIN categories ON categories.id = pages.category_id" +
					" WHERE page_id = ? AND " + TABLE + ".visible = 1" + 
					" GROUP BY page_id";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				count.setPage_id(id);
				count.setComments_count(rs.getInt("licznik"));
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
		
		return count;
	}

	public List<Counts_Dao> getFoundViewsCounts(String search) throws SQLException, ParseException {

		List<Counts_Dao> counts = new ArrayList<Counts_Dao>();
		String search_mask = search.replaceAll(" ", "%");
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT page_id, counter AS licznik" +
					" FROM views" + 
					" INNER JOIN pages ON pages.id = views.page_id" +
					" INNER JOIN categories ON categories.id = pages.category_id" +
					" WHERE (contents LIKE ? OR title LIKE ? OR description LIKE ? OR caption LIKE ?) AND pages.visible = 1" + 
					" GROUP BY page_id";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, "%" + search_mask + "%");
			preparedStatement.setString(2, "%" + search_mask + "%");
			preparedStatement.setString(3, "%" + search_mask + "%");
			preparedStatement.setString(4, "%" + search_mask + "%");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				Counts_Dao count = new Counts_Dao();
				
				count.setPage_id(rs.getInt("page_id"));
				count.setComments_count(rs.getInt("licznik"));
				
				counts.add(count);
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
		
		return counts;
	}

	public int save() throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "INSERT INTO " + TABLE +
					" (id, page_id, nick, email, ip, comment, visible, modified) VALUES" +
					" (NULL, ?, ?, ?, ?, ?, ?, NOW())";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, comment.getPage_id());
			preparedStatement.setString(2, comment.getNick());
			preparedStatement.setString(3, comment.getEmail());
			preparedStatement.setString(4, comment.getIp());
			preparedStatement.setString(5, comment.getComment());
			preparedStatement.setString(6, comment.isVisible() ? "1" : "0");
			
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
					" SET comment = ?, visible = ?" +
					" WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, comment.getComment());
			preparedStatement.setString(2, comment.isVisible() ? "1" : "0");
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
