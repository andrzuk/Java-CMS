package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import utilities.Dates;

public class Pages_Dao {
	
	private int id;
	private String type;
	private int category_id;
	private String caption;
	private String title;
	private String contents;
	private String description;
	private int author_id;
	private String login;
	private boolean visible;
	private Date modified;
	private String modified_short;
	
	public int getId() {
	
		return id;
	}
	
	public void setId(int id) {
	
		this.id = id;
	}
	
	public String getType() {
	
		return type;
	}
	
	public void setType(String type) {
	
		this.type = type;
	}
	
	public int getCategory_id() {
	
		return category_id;
	}
	
	public void setCategory_id(int category_id) {
	
		this.category_id = category_id;
	}
	
	public String getTitle() {
	
		return title;
	}
	
	public void setTitle(String title) {
	
		this.title = title;
	}
	
	public String getContents() {
	
		return contents;
	}
	
	public void setContents(String contents) {
	
		this.contents = contents;
	}
	
	public String getDescription() {
	
		return description;
	}
	
	public void setDescription(String description) {
	
		this.description = description;
	}
	
	public int getAuthor_id() {
	
		return author_id;
	}
	
	public void setAuthor_id(int author_id) {
	
		this.author_id = author_id;
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

	public String getLogin() {
	
		return login;
	}
	
	public void setLogin(String login) {
	
		this.login = login;
	}
	
	public String getCaption() {
	
		return caption;
	}
	
	public void setCaption(String caption) {
	
		this.caption = caption;
	}
}
