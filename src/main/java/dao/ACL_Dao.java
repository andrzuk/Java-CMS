package dao;

public class ACL_Dao {
	
	private int id;
	private int user_id;
	private int function_id;
	private String function_name;
	private String module;
	private boolean access;
	
	public int getId() {
	
		return id;
	}
	
	public void setId(int id) {
	
		this.id = id;
	}
	
	public int getUser_id() {
	
		return user_id;
	}
	
	public void setUser_id(int user_id) {
	
		this.user_id = user_id;
	}
	
	public int getFunction_id() {
	
		return function_id;
	}
	
	public void setFunction_id(int function_id) {
	
		this.function_id = function_id;
	}	
	
	public String getFunction_name() {
	
		return function_name;
	}
	
	public void setFunction_name(String function_name) {
	
		this.function_name = function_name;
	}
	
	public String getModule() {
	
		return module;
	}
	
	public void setModule(String module) {
	
		this.module = module;
	}

	public boolean isAccess() {
	
		return access;
	}
	
	public void setAccess(boolean access) {
	
		this.access = access;
	} 
}
