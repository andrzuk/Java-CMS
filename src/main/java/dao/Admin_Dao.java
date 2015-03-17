package dao;

import java.util.Date;

public class Admin_Dao {
	
	private String modulename;
	private int actives;
	private int all;
	private Date last_item;
	private String link;
	private String image;
	private String title;
	
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
	
	public void setLast_item(Date last_item) {
	
		this.last_item = last_item;
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
}
