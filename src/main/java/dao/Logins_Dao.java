package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import utilities.Dates;

public class Logins_Dao {
	
	private int id;
	private String agent;
	private String user_ip;
	private int user_id;
	private String login;
	private String password;
	private Date login_time;
	private String login_short;
	
	public int getId() {
	
		return id;
	}
	
	public void setId(int id) {
	
		this.id = id;
	}
	
	public String getAgent() {
	
		return agent;
	}
	
	public void setAgent(String agent) {
	
		this.agent = agent;
	}
	
	public String getUser_ip() {
	
		return user_ip;
	}
	
	public void setUser_ip(String user_ip) {
	
		this.user_ip = user_ip;
	}
	
	public int getUser_id() {
	
		return user_id;
	}
	
	public void setUser_id(int user_id) {
	
		this.user_id = user_id;
	}
	
	public String getLogin() {
	
		return login;
	}
	
	public void setLogin(String login) {
	
		this.login = login;
	}
	
	public String getPassword() {
	
		return password;
	}
	
	public void setPassword(String password) {
	
		this.password = password;
	}
	
	public Date getLogin_time() {
	
		return login_time;
	}
	
	public void setLogin_time(Date login_time) throws SQLException, ParseException {
	
		this.login_time = login_time;
		
		Dates myDate = new Dates();
		
		setLogin_short(myDate.getShortDate(login_time));
	}
	
	public String getLogin_short() {
	
		return login_short;
	}

	public void setLogin_short(String login_short) {
	
		this.login_short = login_short;
	}
}
