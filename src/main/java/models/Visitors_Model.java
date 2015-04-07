package models;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import utilities.Paginator;
import utilities.Sorting;
import dao.Visitors_Dao;
import models.Excludes_Model;

public class Visitors_Model {

	private Visitors_Dao visitor;
	
	private String filter;
	
	public Visitors_Model() {		
	}

	public Visitors_Model(Visitors_Dao visitor) {

		this.visitor = visitor;
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
		
        String result = "visitor_ip NOT IN (" + sb.toString().replaceAll(", $", "") + ")" +
                        " AND (visitor_ip LIKE '%" + filter + "%'" +
        		        " OR http_referer LIKE '%" + filter + "%'" +
        		        " OR request_uri LIKE '%" + filter + "%')";

		return result;
	}

	public int save() throws SQLException {

		int result = 0;
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "INSERT INTO visitors"
					+ " (id, visitor_ip, http_referer, request_uri, visited) VALUES"
					+ " (NULL, ?, ?, ?, NOW())";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setString(1, visitor.getVisitor_ip());
			preparedStatement.setString(2, visitor.getHttp_referer());
			preparedStatement.setString(3, visitor.getRequest_uri());
			
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

	public List<Visitors_Dao> getSegment(Paginator paginator, Sorting sorting) throws SQLException, ParseException {

		List<Visitors_Dao> visitors = new ArrayList<Visitors_Dao>();
		Config_Model config = new Config_Model();
		
		int maxLength = Integer.parseInt(config.getConfig("http_referer_length"));
		String referer = null;
		String query = null;
		InetAddress inet_address = null;
		PreparedStatement preparedStatement = null;
		
		int starting_from = paginator.getRows_per_page() * paginator.getPage_index();
		String starting = new Integer(starting_from).toString();
		String showing = new Integer(paginator.getRows_per_page()).toString();

		try {
			
			query = "SELECT *, visitor_ip AS host_name FROM visitors" +
			        " WHERE " + getCondition() + 
					" ORDER BY " + sorting.getSort_field() + " " + sorting.getSort_order() +
			        " LIMIT " + starting + ", " + showing;

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				inet_address = InetAddress.getByName(rs.getString("visitor_ip"));
				referer = rs.getString("http_referer");
				referer = referer.length() > maxLength ? referer.substring(0, maxLength) : referer;
				
				visitor = new Visitors_Dao();
                
                visitor.setId(rs.getInt("id"));
                visitor.setVisitor_ip(rs.getString("visitor_ip"));
                visitor.setHost_name(inet_address.getHostName());
                visitor.setHttp_referer(referer);
                visitor.setRequest_uri(rs.getString("request_uri"));
                visitor.setVisited(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("visited")));
                visitors.add(visitor);
            }			
		} 
		catch (SQLException e) {

			System.out.println(e.getMessage());
		} 
		catch (UnknownHostException e) {
		
			e.printStackTrace();
		} 
		finally {

			if (preparedStatement != null) {

				preparedStatement.close();
			}
			if (db.Connect.getDbConnection() != null) {

				db.Connect.getDbConnection().close();
			}
		}
		
		return visitors;
	}
	
	public int getCount() throws SQLException {

		int counter = 0;
		
		String query = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT COUNT(*) AS licznik FROM visitors" +
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
	
	public Visitors_Dao getOne(int id) throws SQLException, ParseException {

		Visitors_Dao visitor = null;
		
		String referer = null;
		String query = null;
		InetAddress inet_address = null;
		PreparedStatement preparedStatement = null;

		try {
			
			query = "SELECT *, visitor_ip AS host_name FROM visitors" +
					" WHERE id = ?";

			preparedStatement = db.Connect.getDbConnection().prepareStatement(query);
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				inet_address = InetAddress.getByName(rs.getString("visitor_ip"));
				referer = rs.getString("http_referer");
				
				visitor = new Visitors_Dao();
                
                visitor.setId(rs.getInt("id"));
                visitor.setVisitor_ip(rs.getString("visitor_ip"));
                visitor.setHost_name(inet_address.getHostName());
                visitor.setHttp_referer(referer);
                visitor.setRequest_uri(rs.getString("request_uri"));
                visitor.setVisited(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("visited")));
            }			
		} 
		catch (SQLException e) {

			System.out.println(e.getMessage());
		} 
		catch (UnknownHostException e) {
		
			e.printStackTrace();
		} 
		finally {

			if (preparedStatement != null) {

				preparedStatement.close();
			}
			if (db.Connect.getDbConnection() != null) {

				db.Connect.getDbConnection().close();
			}
		}
		
		return visitor;
	}
}
