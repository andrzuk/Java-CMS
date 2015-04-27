package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import utilities.Dates;

public class Images_Dao {
	
	private int id;
	private String file_name;
	private long file_size;
	private long width;
	private long height;
	private int author_id;
	private String login;
	private Date modified;
	private String modified_short;
	
	public int getId() {
	
		return id;
	}
	
	public void setId(int id) {
	
		this.id = id;
	}
	
	public String getFile_name() {
	
		return file_name;
	}
	
	public void setFile_name(String file_name) {
	
		this.file_name = file_name;
	}
	
	public int getAuthor_id() {
	
		return author_id;
	}
	
	public void setAuthor_id(int author_id) {
	
		this.author_id = author_id;
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

	public String getLogin() {
	
		return login;
	}
	
	public void setLogin(String login) {
	
		this.login = login;
	}

	public long getFile_size() {
		
		return file_size;
	}

	public void setFile_size(long file_size) {
		
		this.file_size = file_size;
	}

	public long getWidth() {
		
		return width;
	}

	public void setWidth(long width) {
		
		this.width = width;
	}

	public long getHeight() {
		
		return height;
	}

	public void setHeight(long height) {
		
		this.height = height;
	}
}
