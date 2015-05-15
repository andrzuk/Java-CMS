package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import utilities.Dates;

public class Archives_Dao {

	private int id;
	private Date modified;
	private String modified_short;
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
	
		this.id = id;
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
