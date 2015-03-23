package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.Admin_Dao;

public class Admin_Model {

	public Admin_Model() {
	}

	public List<Admin_Dao> getStatistics() throws SQLException, ParseException {

		String module_name = null;
		Admin_Dao stat_item = null;
		List<Admin_Dao> statistics = new ArrayList<Admin_Dao>();

		module_name = "categories";
		stat_item = new Admin_Dao();
		stat_item.setModule(module_name);
		stat_item.setActives(getActives(module_name, "visible", "1"));
		stat_item.setAll(getAll(module_name));
		stat_item.setLast_item(getLast(module_name, "modified"));
		stat_item.setLink("/categories");
		stat_item.setImage("images/folder_page.png");
		stat_item.setTitle("Kategorie");
		statistics.add(stat_item);
		
		module_name = "pages";
		stat_item = new Admin_Dao();
		stat_item.setModule(module_name);
		stat_item.setActives(getActives(module_name, "visible", "1"));
		stat_item.setAll(getAll(module_name));
		stat_item.setLast_item(getLast(module_name, "modified"));
		stat_item.setLink("/pages");
		stat_item.setImage("images/page.png");
		stat_item.setTitle("Podstrony");
		statistics.add(stat_item);
		
		module_name = "comments";
		stat_item = new Admin_Dao();
		stat_item.setModule(module_name);
		stat_item.setActives(getActives(module_name, "visible", "0"));
		stat_item.setAll(getAll(module_name));
		stat_item.setLast_item(getLast(module_name, "modified"));
		stat_item.setLink("/comments");
		stat_item.setImage("images/reports.png");
		stat_item.setTitle("Komentarze");
		statistics.add(stat_item);
		
		module_name = "messages";
		stat_item = new Admin_Dao();
		stat_item.setModule(module_name);
		stat_item.setActives(getActives(module_name, "visible", "0"));
		stat_item.setAll(getAll(module_name));
		stat_item.setLast_item(getLast(module_name, "modified"));
		stat_item.setLink("/messages");
		stat_item.setImage("images/mail_message.png");
		stat_item.setTitle("Wiadomości");
		statistics.add(stat_item);
		
		return statistics;
	}

	public List<Admin_Dao> getModules() throws SQLException, ParseException {

		String module_name = null;
		Admin_Dao module_item = null;
		List<Admin_Dao> modules = new ArrayList<Admin_Dao>();

		module_name = "config";
		module_item = new Admin_Dao();
		module_item.setModule(module_name);
		module_item.setLink("/" + module_name);
		module_item.setImage("images/config.png");
		module_item.setTitle("Konfiguracja");
		modules.add(module_item);
		
		module_name = "categories";
		module_item = new Admin_Dao();
		module_item.setModule(module_name);
		module_item.setLink("/" + module_name);
		module_item.setImage("images/folder_page.png");
		module_item.setTitle("Kategorie");
		modules.add(module_item);
		
		module_name = "pages";
		module_item = new Admin_Dao();
		module_item.setModule(module_name);
		module_item.setLink("/" + module_name);
		module_item.setImage("images/page.png");
		module_item.setTitle("Podstrony");
		modules.add(module_item);
		
		module_name = "comments";
		module_item = new Admin_Dao();
		module_item.setModule(module_name);
		module_item.setLink("/" + module_name);
		module_item.setImage("images/reports.png");
		module_item.setTitle("Komentarze");
		modules.add(module_item);
		
		module_name = "messages";
		module_item = new Admin_Dao();
		module_item.setModule(module_name);
		module_item.setLink("/" + module_name);
		module_item.setImage("images/mail_message.png");
		module_item.setTitle("Wiadomości");
		modules.add(module_item);
		
		module_name = "images";
		module_item = new Admin_Dao();
		module_item.setModule(module_name);
		module_item.setLink("/" + module_name);
		module_item.setImage("images/picture.png");
		module_item.setTitle("Galeria");
		modules.add(module_item);
	    
		module_name = "users";
		module_item = new Admin_Dao();
		module_item.setModule(module_name);
		module_item.setLink("/" + module_name);
		module_item.setImage("images/user.png");
		module_item.setTitle("Użytkownicy");
		modules.add(module_item);

		module_name = "acl";
		module_item = new Admin_Dao();
		module_item.setModule(module_name);
		module_item.setLink("/" + module_name);
		module_item.setImage("images/acl.png");
		module_item.setTitle("ACL");
		modules.add(module_item);

		module_name = "logins";
		module_item = new Admin_Dao();
		module_item.setModule(module_name);
		module_item.setLink("/" + module_name);
		module_item.setImage("images/password.png");
		module_item.setTitle("Logowania");
		modules.add(module_item);

		module_name = "visitors";
		module_item = new Admin_Dao();
		module_item.setModule(module_name);
		module_item.setLink("/" + module_name);
		module_item.setImage("images/internet.png");
		module_item.setTitle("Odwiedziny");
		modules.add(module_item);

		module_name = "excludes";
		module_item = new Admin_Dao();
		module_item.setModule(module_name);
		module_item.setLink("/" + module_name);
		module_item.setImage("images/list_checked.png");
		module_item.setTitle("Wykluczenia");
		modules.add(module_item);
		
		return modules;
	}

	private int getActives(String module_name, String visible, String state) throws SQLException, ParseException {

		int result = 0;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT COUNT(*) AS licznik" +
					" FROM " + module_name +
					" WHERE " + visible + " = " + state;

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                result = rs.getInt("licznik");
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
	
	private int getAll(String module_name) throws SQLException, ParseException {

		int result = 0;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT COUNT(*) AS licznik" +
					" FROM " + module_name;

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                result = rs.getInt("licznik");
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
	
	private Date getLast(String module_name, String modified) throws SQLException, ParseException {

		Date result = null;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT " + modified + " AS last_item" +
					" FROM " + module_name +
					" ORDER BY " + modified + " DESC LIMIT 1";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
                result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("last_item"));                
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
