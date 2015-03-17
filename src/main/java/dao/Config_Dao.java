package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import utilities.Dates;

public class Config_Dao {
	
	private int id;
	private String key_name;
	private String key_value;
	private String meaning;
	private int field_type;
	private boolean active;
	private Date modified;
	private String modified_short;
	
	public int getId() {
	
		return id;
	}
	
	public void setId(int id) {
	
		this.id = id;
	}
	
	public String getKey_name() {
	
		return key_name;
	}
	
	public void setKey_name(String key_name) {
	
		this.key_name = key_name;
	}
	
	public String getKey_value() {
	
		return key_value;
	}
	
	public void setKey_value(String key_value) {
	
		this.key_value = key_value;
	}
	
	public String getMeaning() {
	
		return meaning;
	}
	
	public void setMeaning(String meaning) {
	
		this.meaning = meaning;
	}
	
	public int getField_type() {
	
		return field_type;
	}
	
	public void setField_type(int field_type) {
	
		this.field_type = field_type;
	}
	
	public boolean isActive() {
	
		return active;
	}
	
	public void setActive(boolean active) {
	
		this.active = active;
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
