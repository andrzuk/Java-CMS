package validators;

import dao.Images_Dao;

public class Images_Validator {

	public boolean validate(Images_Dao image) {
		
		if (image == null) return false;
		
		if (image.getFile_name().isEmpty()) {
			
			return false;
		}
		
		return true;
	}
}
