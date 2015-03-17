package validators;

import dao.Pages_Dao;

public class Pages_Validator {

	public boolean validate(Pages_Dao page) {
		
		if (page == null) return false;
		
		if (page.getTitle().isEmpty() || page.getContents().isEmpty() || page.getDescription().isEmpty()) {
			
			return false;
		}
		
		return true;
	}
}
