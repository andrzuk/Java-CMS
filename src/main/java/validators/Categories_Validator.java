package validators;

import dao.Categories_Dao;

public class Categories_Validator {

	public boolean validate(Categories_Dao category) {
		
		if (category == null) return false;
		
		if (category.getCaption().isEmpty() || category.getLink().isEmpty()) {
			
			return false;
		}
		
		return true;
	}
}
