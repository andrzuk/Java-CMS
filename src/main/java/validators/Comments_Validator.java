package validators;

import dao.Comments_Dao;

public class Comments_Validator {

	public boolean validate(Comments_Dao comment) {
		
		if (comment == null) return false;
		
		if (comment.getPage_id() == 0 || comment.getNick().isEmpty() || comment.getEmail().isEmpty() || comment.getComment().isEmpty()) {
			
			return false;
		}
		
		return true;
	}
}

