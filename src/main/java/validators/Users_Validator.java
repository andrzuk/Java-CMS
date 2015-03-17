package validators;

import dao.Users_Dao;

public class Users_Validator {

	public boolean validate(Users_Dao user) {
		
		if (user == null) return false;
		
		if (user.getFirst_name().isEmpty() || user.getLast_name().isEmpty() || user.getLogin().isEmpty() || user.getEmail().isEmpty()) {
			
			return false;
		}
		
		Email_Validator email_validator = new Email_Validator();
		
		return email_validator.validate(user.getEmail());
	}
	
	public boolean compare(Users_Dao user) {
		
		if (user == null) return false;
		
		if (user.getPassword().isEmpty() || user.getRepeat().isEmpty()) {
			
			return false;
		}
		
		return user.getPassword().equals(user.getRepeat());
	}
	
	public boolean check(Users_Dao user) {
		
		if (user == null) return false;
		
		if (user.getLogin().isEmpty() || user.getPassword().isEmpty()) {
			
			return false;
		}
		
		return true;
	}
}
