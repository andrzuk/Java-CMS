package dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import utilities.Dates;

public class Users_Dao {
	
	private int id;
	private String login;
	private String password;
	private String repeat;
	private String first_name;
	private String last_name;
	private String email;
	private int status;
	private Date registered;
	private Date logged_in;
	private Date modified;
	private String modified_short;
	private Date logged_out;
	private boolean active;
	private String available_functions;

	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	
	public String getLogin() {
		
		return login;
	}
	
	public void setLogin(String user_login) {
		
		this.login = user_login;
	}
	
	public String getPassword() {
		
		return password;
	}
	
	public void setPassword(String user_password) {
		
		this.password = user_password;
	}
	
	public String getRepeat() {
		
		return repeat;
	}
	
	public void setRepeat(String user_password) {
		
		this.repeat = user_password;
	}
	
	public String getEncodedPassword() throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		md.update(password.getBytes());
		byte[] digest = md.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		
		return bigInt.toString(16);
	}
	
	public String getFirst_name() {
		
		return first_name;
	}
	
	public void setFirst_name(String first_name) {
		
		this.first_name = first_name;
	}
	
	public String getLast_name() {
		
		return last_name;
	}
	
	public void setLast_name(String last_name) {
		
		this.last_name = last_name;
	}
	
	public String getEmail() {
		
		return email;
	}
	
	public void setEmail(String email) {
		
		this.email = email;
	}
	
	public int getStatus() {
		
		return status;
	}
	
	public void setStatus(int status) {
		
		this.status = status;
	}
	
	public Date getRegistered() {
		
		return registered;
	}
	
	public void setRegistered(Date registered) {
		
		this.registered = registered;
	}
	
	public Date getLogged_in() {
		
		return logged_in;
	}
	
	public void setLogged_in(Date logged_in) {
		
		this.logged_in = logged_in;
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

	public Date getLogged_out() {
		
		return logged_out;
	}
	
	public void setLogged_out(Date logged_out) {
		
		this.logged_out = logged_out;
	}
	
	public boolean isActive() {
		
		return active;
	}
	
	public void setActive(boolean active) {
		
		this.active = active;
	}
	
	public String getAvailable_functions() {
	
		return available_functions;
	}
	
	public void setAvailable_functions(String available_functions) {
	
		this.available_functions = available_functions;
	}
}
