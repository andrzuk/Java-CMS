package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import utilities.Dates;

public class Categories_Dao {

	private int id;
	private int parent_id;
	private int item_order;
	private String caption;
	private String link;
	private boolean visible;
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
	
	public int getParent_id() {
	
		return parent_id;
	}
	
	public void setParent_id(int parent_id) {
	
		this.parent_id = parent_id;
	}
	
	public int getItem_order() {
	
		return item_order;
	}
	
	public void setItem_order(int item_order) {
	
		this.item_order = item_order;
	}
	
	public String getCaption() {
	
		return caption;
	}
	
	public void setCaption(String caption) {
	
		this.caption = caption;
	}
	
	public String getLink() {
	
		return link;
	}
	
	public void setLink(String link) {
	
		this.link = link;
	}
	
	public boolean isVisible() {
	
		return visible;
	}
	
	public void setVisible(boolean visible) {
	
		this.visible = visible;
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
}
