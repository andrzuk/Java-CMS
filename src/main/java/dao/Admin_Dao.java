package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import utilities.Dates;

public class Admin_Dao {
	
	private String modulename;
	private int actives;
	private int all;
	private Date last_item;
	private String link;
	private String image;
	private String title;
	private String last_item_short;
	private boolean two_counters;
	private boolean show_last;
	
	public String getModule() {
	
		return modulename;
	}
	
	public void setModule(String modulename) {
	
		this.modulename = modulename;
	}
	
	public int getActives() {
	
		return actives;
	}
	
	public void setActives(int actives) {
	
		this.actives = actives;
	}
	
	public int getAll() {
	
		return all;
	}
	
	public void setAll(int all) {
	
		this.all = all;
	}
	
	public Date getLast_item() {
	
		return last_item;
	}
	
	public void setLast_item(Date last_item) throws SQLException, ParseException {
	
		this.last_item = last_item;
		
		Dates myDate = new Dates();
		
		setLast_item_short(myDate.getShortDate(last_item));
	}
	
	public String getLast_item_short() {
		
		return last_item_short;
	}

	public void setLast_item_short(String last_item_short) {
		
		this.last_item_short = last_item_short;
	}

	public String getLink() {
	
		return link;
	}
	
	public void setLink(String link) {
	
		this.link = link;
	}
	
	public String getImage() {
	
		return image;
	}
	
	public void setImage(String image) {
	
		this.image = image;
	}
	
	public String getTitle() {
	
		return title;
	}
	
	public void setTitle(String title) {
	
		this.title = title;
	}

	public boolean isTwo_counters() {
		
		return two_counters;
	}

	public void setTwo_counters(boolean two_counters) {
		
		this.two_counters = two_counters;
	}

	public boolean isShow_last() {
		
		return show_last;
	}

	public void setShow_last(boolean show_last) {
		
		this.show_last = show_last;
	}
}
