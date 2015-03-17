package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import utilities.Paginator;
import utilities.Sorting;
import dao.ACL_Dao;
import dao.Users_Dao;

public class ACL_Model {
	
	private static final String TABLE = "users";

	private ACL_Dao acl;
	private List<ACL_Dao> acls;
	
	private String filter;

	public ACL_Model() {
	}

	public ACL_Model(ACL_Dao acl) {

		this.acl = acl;
	}

	public ACL_Model(List<ACL_Dao> acls) {

		this.acls = acls;
	}

	private Users_Dao setRecord(ResultSet rs, String available_functions, Users_Dao user) throws SQLException, ParseException {
		
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setStatus(rs.getInt("status"));
        user.setActive(rs.getBoolean("active"));
        user.setAvailable_functions(available_functions);
        
        return user;
	}
	
	public void setFilter(String filter) {
		
		this.filter = filter != null ? filter : "";
	}

	private String getCondition() throws SQLException {
		
        String result = "login LIKE '%" + filter + "%'" +
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
		String functions = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement subStatement = null;

		int starting_from = paginator.getRows_per_page() * paginator.getPage_index();
		String starting = new Integer(starting_from).toString();
		String showing = new Integer(paginator.getRows_per_page()).toString();

		try {
			
			query = "SELECT *, NULL AS functions FROM " + TABLE +
					" WHERE " + getCondition() + 
					" ORDER BY " + sorting.getSort_field() + " " + sorting.getSort_order() +
			        " LIMIT " + starting + ", " + showing;

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				query = "SELECT module, meaning, user_roles.access FROM user_roles" +
						" INNER JOIN admin_functions ON admin_functions.id = user_roles.function_id" +
						" WHERE user_id = ?" + 
						" ORDER BY admin_functions.id";

				subStatement = db.Connect.getDbConnection().prepareStatement(query);
				
				subStatement.setInt(1, rs.getInt("id"));
				
				ResultSet as = subStatement.executeQuery();
				
				functions = "";

				while (as.next()) {
					
					if (as.getBoolean("access")) {
						
		                functions += as.getString("meaning") + "; ";						
					}
	            }			
				
				users.add(setRecord(rs, functions, new Users_Dao()));
            }			
		} 
		catch (SQLException e) {

			System.out.println(e.getMessage());
		} 
		finally {

			if (preparedStatement != null) {

				preparedStatement.close();
			}
			if (subStatement != null) {

				subStatement.close();
			}
			if (db.Connect.getDbConnection() != null) {

				db.Connect.getDbConnection().close();
			}
		}
		
		return users;
	}

	public List<ACL_Dao> getOne(int id) throws SQLException {

		List<ACL_Dao> acls = new ArrayList<ACL_Dao>();
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT user_roles.*, module, meaning" +
					" FROM user_roles" +
					" INNER JOIN admin_functions ON admin_functions.id = user_roles.function_id" +
					" WHERE user_id = ?" +
					" ORDER BY id";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                acl = new ACL_Dao();
                
                acl.setId(rs.getInt("id"));
                acl.setUser_id(rs.getInt("user_id"));
                acl.setFunction_id(rs.getInt("function_id"));
                acl.setModule(rs.getString("module"));
                acl.setFunction_name(rs.getString("meaning"));
                acl.setAccess(rs.getBoolean("access"));
                acls.add(acl);
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
		
		return acls;
	}

	public int saveAll(int id) throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {

			query = "DELETE FROM user_roles WHERE user_id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
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

			query = "INSERT INTO user_roles"
					+ " (id, user_id, function_id, access) VALUES"
					+ " (NULL, ?, ?, ?)";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			for (int i = 0; i < acls.size(); i++) {
				
				preparedStatement.setInt(1, acls.get(i).getUser_id());
				preparedStatement.setInt(2, acls.get(i).getFunction_id());
				preparedStatement.setInt(3, acls.get(i).isAccess() ? 1 : 0);
				
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
	
	public int makeAccess(int id) throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement subStatement = null;

		try {

			query = "INSERT INTO user_roles"
					+ " (id, user_id, function_id, access) VALUES"
					+ " (NULL, ?, ?, ?)";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			query = "SELECT * FROM admin_functions ORDER BY id";

			subStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet as = subStatement.executeQuery();

			while (as.next()) {
				
				preparedStatement.setInt(1, id);
				preparedStatement.setInt(2, as.getInt("id"));
				preparedStatement.setInt(3, 0);
				
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
			if (subStatement != null) {

				subStatement.close();
			}
			if (db.Connect.getDbConnection() != null) {

				db.Connect.getDbConnection().close();
			}
		}

		return result;
	}
	
	public boolean getAccess(int user_id, String module) throws SQLException {

		boolean result = false;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT access FROM user_roles" +
					" INNER JOIN admin_functions ON admin_functions.id = user_roles.function_id" +
			        " WHERE user_id = ? AND module = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, user_id);
			preparedStatement.setString(2, module);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                result = rs.getBoolean("access");
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
}
