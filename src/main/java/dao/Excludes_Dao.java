package dao;

public class Excludes_Dao {

	private int id;
	private String visitor_ip;
	private boolean active;
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	
	public String getVisitor_ip() {
		
		return visitor_ip;
	}
	
	public void setVisitor_ip(String visitor_ip) {
		
		this.visitor_ip = visitor_ip;
	}
	
	public boolean isActive() {
		
		return active;
	}
	
	public void setActive(boolean active) {
		
		this.active = active;
	}
}
