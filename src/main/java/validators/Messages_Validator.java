package validators;

import dao.Messages_Dao;

public class Messages_Validator {

	public boolean validate(Messages_Dao message) {
		
		if (message == null) return false;
		
		if (message.getNick().isEmpty() || message.getEmail().isEmpty() || message.getMessage().isEmpty()) {
			
			return false;
		}
		
		return true;
	}
}

