package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.Pages_Dao;
import dao.Archives_Dao;
import utilities.Paginator;
import utilities.Sorting;
import utilities.Descriptions;

public class Pages_Model {

	private static final String TABLE = "pages";
	
	private Pages_Dao page;
	
	private String filter;

	public Pages_Model() {
	}

	public Pages_Model(Pages_Dao page) {

		this.page = page;
	}
	
	private Pages_Dao setRecord(ResultSet rs, Pages_Dao page) throws SQLException, ParseException {
		
        page.setId(rs.getInt("id"));
        page.setType(rs.getString("type"));
        page.setCategory_id(rs.getInt("category_id"));
        page.setCaption(rs.getString("caption"));
        page.setTitle(rs.getString("title"));
        page.setContents(rs.getString("contents"));
        page.setDescription(rs.getString("description"));
        page.setAuthor_id(rs.getInt("author_id"));
        page.setLogin(rs.getString("login"));
        page.setVisible(rs.getBoolean("visible"));
        page.setModified(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("modified")));
        
        return page;
	}
	
	public void setFilter(String filter) {
		
		this.filter = filter != null ? filter : "";
	}

	private String getCondition() throws SQLException {
		
        String result = "title LIKE '%" + filter + "%'" +
                        " OR contents LIKE '%" + filter + "%'" +
                        " OR description LIKE '%" + filter + "%'";

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

	public List<Pages_Dao> getSegment(Paginator paginator, Sorting sorting) throws SQLException, ParseException {

		List<Pages_Dao> pages = new ArrayList<Pages_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		int starting_from = paginator.getRows_per_page() * paginator.getPage_index();
		String starting = new Integer(starting_from).toString();
		String showing = new Integer(paginator.getRows_per_page()).toString();

		try {
			
			query = "SELECT " + TABLE + ".*, categories.caption, users.login" +
					" FROM " + TABLE +
					" INNER JOIN categories ON categories.id = " + TABLE + ".category_id" +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" WHERE " + getCondition() + 
					" ORDER BY " + sorting.getSort_field() + " " + sorting.getSort_order() +
			        " LIMIT " + starting + ", " + showing;

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                pages.add(setRecord(rs, new Pages_Dao()));
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
		
		return pages;
	}

	public Pages_Dao getOne(int id) throws SQLException, ParseException {

		Pages_Dao page = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, categories.caption, users.login" + 
					" FROM " + TABLE +
					" INNER JOIN categories ON categories.id = " + TABLE + ".category_id" +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" WHERE " + TABLE + ".id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				page = setRecord(rs, new Pages_Dao());
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
		
		return page;
	}

	public Pages_Dao getActive(int id) throws SQLException, ParseException {

		Pages_Dao page = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, categories.caption, users.login" + 
					" FROM " + TABLE +
					" INNER JOIN categories ON categories.id = " + TABLE + ".category_id" +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" WHERE " + TABLE + ".id = ? AND " + TABLE + ".visible = 1";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				page = setRecord(rs, new Pages_Dao());
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
		
		return page;
	}

	public Pages_Dao getIndexPage() throws SQLException, ParseException {

		Pages_Dao page = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, categories.caption, users.login" + 
					" FROM " + TABLE +
					" INNER JOIN categories ON categories.id = " + TABLE + ".category_id" +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" WHERE categories.caption = ? AND " + TABLE + ".visible = 1" +
					" ORDER BY id DESC LIMIT 1";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, "Index");
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				page = setRecord(rs, new Pages_Dao());
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
		
		return page;
	}

	public Pages_Dao getContactPage() throws SQLException, ParseException {

		Pages_Dao page = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, categories.caption, users.login" + 
					" FROM " + TABLE +
					" INNER JOIN categories ON categories.id = " + TABLE + ".category_id" +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" WHERE categories.caption = ? AND " + TABLE + ".visible = 1" +
					" ORDER BY id DESC LIMIT 1";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, "Contact");
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				page = setRecord(rs, new Pages_Dao());
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
		
		return page;
	}

	public List<Pages_Dao> getArticleHeaders(int id) throws SQLException, ParseException {

		List<Pages_Dao> pages = new ArrayList<Pages_Dao>();
		Descriptions description = new Descriptions();
		Config_Model config = new Config_Model();
		String shortContent = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, categories.caption, users.login" +
					" FROM " + TABLE +
					" INNER JOIN categories ON categories.id = " + TABLE + ".category_id" +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" WHERE category_id = ? AND " + TABLE + ".visible = 1" + 
					" ORDER BY id";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				page = setRecord(rs, new Pages_Dao());
				
				shortContent = description.removeTags(page.getContents());
				page.setContents(description.getIntroduce(shortContent, Integer.parseInt(config.getConfig("description_words_pages"))));
				
                pages.add(page);
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
		
		return pages;
	}

	public List<Pages_Dao> getFoundHeaders(String search) throws SQLException, ParseException {

		List<Pages_Dao> pages = new ArrayList<Pages_Dao>();
		Descriptions description = new Descriptions();
		Config_Model config = new Config_Model();
		String shortContent = null;
		String search_mask = search.replaceAll(" ", "%");
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + TABLE + ".*, categories.caption, users.login" +
					" FROM " + TABLE +
					" INNER JOIN categories ON categories.id = " + TABLE + ".category_id" +
					" INNER JOIN users ON users.id = " + TABLE + ".author_id" +
					" WHERE (contents LIKE ? OR title LIKE ? OR description LIKE ? OR caption LIKE ?) AND " + TABLE + ".visible = 1" + 
					" ORDER BY title";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, "%" + search_mask + "%");
			preparedStatement.setString(2, "%" + search_mask + "%");
			preparedStatement.setString(3, "%" + search_mask + "%");
			preparedStatement.setString(4, "%" + search_mask + "%");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				page = setRecord(rs, new Pages_Dao());
				
				shortContent = description.removeTags(page.getContents());
				page.setContents(description.getIntroduce(shortContent, Integer.parseInt(config.getConfig("description_words_search"))));
				
                pages.add(page);
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
		
		return pages;
	}

	public int save() throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "INSERT INTO " + TABLE +
					" (id, type, category_id, title, contents, description, author_id, visible, modified) VALUES" +
					" (NULL, ?, ?, ?, ?, ?, ?, ?, NOW())";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, page.getType());
			preparedStatement.setInt(2, page.getCategory_id());
			preparedStatement.setString(3, page.getTitle());
			preparedStatement.setString(4, page.getContents());
			preparedStatement.setString(5, page.getDescription());
			preparedStatement.setInt(6, page.getAuthor_id());
			preparedStatement.setString(7, page.isVisible() ? "1" : "0");
			
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
					" SET type = ?, category_id = ?, title = ?, contents = ?, description = ?, author_id = ?, visible = ?, modified = NOW()" +
					" WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, page.getType());
			preparedStatement.setInt(2, page.getCategory_id());
			preparedStatement.setString(3, page.getTitle());
			preparedStatement.setString(4, page.getContents());
			preparedStatement.setString(5, page.getDescription());
			preparedStatement.setInt(6, page.getAuthor_id());
			preparedStatement.setString(7, page.isVisible() ? "1" : "0");
			preparedStatement.setInt(8, id);
			
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
	
	public int archive(int id) throws SQLException, ParseException {

		int result = 0;
		int counter = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		// wczytuje oryginalny rekord:
		
		Pages_Dao page = getOne(id);

		// sprawdza, czy istnieje juz kopia rekordu:
		
		try {
			
			query = "SELECT COUNT(*) AS licznik FROM archives " +
					" WHERE page_id = ? AND title = ? AND description = ? AND contents = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, page.getTitle());
			preparedStatement.setString(3, page.getDescription());
			preparedStatement.setString(4, page.getContents());
			
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
		
		if (counter == 0) { // nie ma jeszcze kopii rekordu
			
			try {
				
				query = "INSERT INTO archives" +
						" (id, page_id, type, category_id, title, contents, description, author_id, visible, modified) VALUES" +
						" (NULL, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
	
				preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
				
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, page.getType());
				preparedStatement.setInt(3, page.getCategory_id());
				preparedStatement.setString(4, page.getTitle());
				preparedStatement.setString(5, page.getContents());
				preparedStatement.setString(6, page.getDescription());
				preparedStatement.setInt(7, page.getAuthor_id());
				preparedStatement.setString(8, page.isVisible() ? "1" : "0");
				
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
		}
				
		return result;
	}
	
	public List<Archives_Dao> getArchives(int id) throws SQLException, ParseException {

		Archives_Dao archive = null;
		List<Archives_Dao> archives = new ArrayList<Archives_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT id, modified FROM archives" +
					" WHERE page_id = ?" + 
					" ORDER BY modified";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
		
				archive = new Archives_Dao();
				
				archive.setId(rs.getInt("id"));
				archive.setModified(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("modified")));
				
				archives.add(archive);
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
		
		return archives;
	}
	
	public int restore(int id, int archive_id) throws SQLException, ParseException {

		Pages_Dao page = null;
		
		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		// odczytuje z archiwum wybraną kopię strony:
		
		try {
			
			query = "SELECT *, NULL AS caption, NULL AS login FROM archives" + 
					" WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, archive_id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				page = setRecord(rs, new Pages_Dao());
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

		if (page == null) return result; 
		
		// przywraca kopię strony:
		
		try {
			
			query = "UPDATE " + TABLE +
					" SET type = ?, category_id = ?, title = ?, contents = ?, description = ?, author_id = ?, visible = ?, modified = NOW()" +
					" WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, page.getType());
			preparedStatement.setInt(2, page.getCategory_id());
			preparedStatement.setString(3, page.getTitle());
			preparedStatement.setString(4, page.getContents());
			preparedStatement.setString(5, page.getDescription());
			preparedStatement.setInt(6, page.getAuthor_id());
			preparedStatement.setString(7, page.isVisible() ? "1" : "0");
			preparedStatement.setInt(8, id);
			
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
