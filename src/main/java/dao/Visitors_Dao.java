package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import utilities.Dates;

public class Visitors_Dao {

	private int id;
	private String visitor_ip;
	private String host_name;
	private String http_referer;
	private String http_referer_split;
	private String request_uri;
	private Date visited;
	private String visited_short;
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	
	public String getVisitor_ip() {
		
		return visitor_ip;
	}
	
	public void setVisitor_ip(String visitor_ip) {
		
		this.visitor_ip = visitor_ip;
	}
	
	public String getHost_name() {
	
		return host_name;
	}
	
	public void setHost_name(String host_name) {
	
		this.host_name = host_name;
	}

	public String getHttp_referer() {
		
		return http_referer;
	}
	
	public void setHttp_referer(String http_referer) {
		
		this.http_referer = http_referer;
	}
	
	public String getHttp_referer_split() {
		
		return http_referer_split;
	}

	public void setHttp_referer_split(String http_referer_split) {
		
		this.http_referer_split = http_referer_split;
	}

	public String getRequest_uri() {
		
		return request_uri;
	}
	
	public void setRequest_uri(String request_uri) {
		
		this.request_uri = request_uri;
	}
	
	public Date getVisited() {
		
		return visited;
	}
	
	public void setVisited(Date visited) throws SQLException, ParseException {
		
		this.visited = visited;
		
		Dates myDate = new Dates();
		
		setVisited_short(myDate.getShortDate(visited));
	}

	public String getVisited_short() {
		
		return visited_short;
	}
	
	public void setVisited_short(String visited_short) {
	
		this.visited_short = visited_short;
	}
}
