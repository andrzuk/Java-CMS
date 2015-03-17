package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import utilities.Dates;

public class Messages_Dao {

	private int id;
	private String nick;
	private String email;
	private String ip;
	private String message;
	private boolean visible;
	private Date modified;
	private String modified_short;
	
	public int getId() {
	
		return id;
	}
	
	public void setId(int id) {
	
		this.id = id;
	}
	
	public String getNick() {
	
		return nick;
	}
	
	public void setNick(String nick) {
	
		this.nick = nick;
	}
	
	public String getEmail() {
	
		return email;
	}
	
	public void setEmail(String email) {
	
		this.email = email;
	}
	
	public String getIp() {
	
		return ip;
	}
	
	public void setIp(String ip) {
	
		this.ip = ip;
	}
	
	public String getMessage() {
	
		return message;
	}
	
	public void setMessage(String message) {
	
		this.message = message;
	}
	
	public boolean isVisible() {
	
		return visible;
	}
	
	public void setVisible(boolean visible) {
	
		this.visible = visible;
	}
	
	public Date getModified() {
	
		return modified;
	}
	
	public void setModified(Date modified) throws SQLException, ParseException {
		
		this.modified = modified;
		
		Dates myDate = new Dates();
		
		setModified_short(myDate.getShortDate(modified));
	}

	public String getModified_short() {
		
		return modified_short;
	}
	
	public void setModified_short(String modified_short) {
	
		this.modified_short = modified_short;
	}
}
