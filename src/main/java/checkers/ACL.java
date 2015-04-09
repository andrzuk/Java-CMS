package checkers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import models.ACL_Model;


public class ACL {
	
	private String profile;
	private int status;
	private String resource;
	private Map<String, Integer> access;
	
	public ACL() {
		
		Map<String, Integer> items = new HashMap<String, Integer>();
		
		items.put("admin", 3);
		items.put("config", 1);
		items.put("styles", 1);
		items.put("categories", 2);
		items.put("pages", 2);
		items.put("comments", 2);
		items.put("messages", 2);
		items.put("users", 1);
		items.put("acl", 1);
		items.put("logins", 1);
		items.put("images", 3);
		items.put("visitors", 2);
		items.put("excludes", 2);
		
		setAccess(items);
	}
	
	public String getProfile() {
	
		return profile;
	}
	
	public void setProfile(String profile) {
	
		this.profile = profile;
	}
	
	public int getStatus() {
	
		return status;
	}
	
	public void setStatus(int status) {
	
		this.status = status;
	}
	
	public String getResource() {
	
		return resource;
	}
	
	public void setResource(String resource) {
	
		this.resource = resource;
	}
	
	public Map<String, Integer> getAccess() {
	
		return access;
	}
	
	public void setAccess(Map<String, Integer> access) {
	
		this.access = access;
	}
	
	public boolean getAccess(Object user_status, Object user_id, String resource) {
		
		if (user_status == null || user_id == null) return false;
		
		if (access.containsKey(resource)) {
			
			if (access.get(resource) >= (Integer) user_status) {
				
				ACL_Model acl = new ACL_Model();
				
				try {
					
					return acl.getAccess((Integer) user_id, resource);
				} 
				catch (SQLException e) {
				
					e.printStackTrace();
					return false;
				}
			}
		}
		
		return false;
	}
}
